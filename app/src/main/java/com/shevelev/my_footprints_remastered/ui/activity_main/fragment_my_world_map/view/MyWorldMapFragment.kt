package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.*
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentMyWorldMapBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.di.MyWorldMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.pins_cache.PinsCache
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view_model.MyWorldMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import javax.inject.Inject

class MyWorldMapFragment : FragmentBaseMVVM<FragmentMyWorldMapBinding, MyWorldMapFragmentViewModel>(), OnMapReadyCallback {

    private var map: GoogleMap? = null

    @Inject
    internal lateinit var pinsCache: PinsCache

    override fun provideViewModelType(): Class<MyWorldMapFragmentViewModel> = MyWorldMapFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_my_world_map

    override fun linkViewModel(binding: FragmentMyWorldMapBinding, viewModel: MyWorldMapFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<MyWorldMapFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<MyWorldMapFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is StartLoadingMap -> startLoadingMap()
        }
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
        map.uiSettings.isZoomControlsEnabled = false

        viewModel.footprints.observe({viewLifecycleOwner.lifecycle}) { showFootprints(it) }
    }

    private fun startLoadingMap() {
        val mapFragment = SupportMapFragment.newInstance(GoogleMapOptions())
        childFragmentManager.beginTransaction().add(R.id.mapContainer, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    private fun showFootprints(footprints: FootprintsOnMap) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(footprints.centerLocation, footprints.zoomFactor))
    }
}