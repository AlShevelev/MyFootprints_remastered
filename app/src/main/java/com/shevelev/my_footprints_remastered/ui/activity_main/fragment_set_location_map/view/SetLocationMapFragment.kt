package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentSetLocationMapBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.di.SetLocationMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.dto.PinInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view_model.SetLocationMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor.SelectColorDialog
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor.TextColors
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveAndZoomMap
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowColorDialog
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand

class SetLocationMapFragment :
    FragmentBaseMVVM<FragmentSetLocationMapBinding,
    SetLocationMapFragmentViewModel>(),
    OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var marker: Marker? = null

    override fun provideViewModelType(): Class<SetLocationMapFragmentViewModel> = SetLocationMapFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_set_location_map

    override fun linkViewModel(binding: FragmentSetLocationMapBinding, viewModel: SetLocationMapFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<SetLocationMapFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<SetLocationMapFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is StartLoadingMap -> startLoadingMap()
            is ShowColorDialog -> showColorDialog(command.textColor, command.backgroundColor)
            is MoveAndZoomMap -> moveAndZoomMap(command.zoomFactor, command.location)
        }
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            SelectColorDialog.REQUEST_CODE -> {
                if(!isCanceled) {
                    (data as TextColors).let { viewModel.onPinColorSelected(it.foregroundColor, it.backgroundColor) }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.pin.observe({viewLifecycleOwner.lifecycle}) { updatePin(it) }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

        // turn off rotation and compass
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isZoomControlsEnabled = true

        viewModel.mapLoaded()
    }

    private fun startLoadingMap() {
        val mapFragment = SupportMapFragment.newInstance(GoogleMapOptions())
        childFragmentManager.beginTransaction().add(R.id.mapContainer, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    private fun moveAndZoomMap(zoomFactor: Float, lastLocation: Location) {
        val mapLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mapLocation, zoomFactor))
    }

    private fun updatePin(pinInfo: PinInfo) {
        if(map == null) {
            return
        }

        val mapLocation = LatLng(pinInfo.location.latitude, pinInfo.location.longitude)

        marker?.remove()

        marker = map!!
            .addMarker(MarkerOptions()
                .position(mapLocation)
                .icon(BitmapDescriptorFactory.fromBitmap(pinInfo.pin.bitmap))
                .anchor(pinInfo.pin.spearheadRelativeX, 1.0f))
    }

    private fun showColorDialog(@ColorInt textColor: Int, @ColorInt backgroundColor: Int) =
        SelectColorDialog.show(this, textColor, backgroundColor, R.string.timesSquare)
}