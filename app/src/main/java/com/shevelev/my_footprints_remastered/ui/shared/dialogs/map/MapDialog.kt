package com.shevelev.my_footprints_remastered.ui.shared.dialogs.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.shared.Constants
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.utils.resources.getScreenSize
import kotlinx.android.synthetic.main.dialog_map.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapDialog : BottomSheetDialogFragmentBase(), OnMapReadyCallback {
    companion object {
        private const val FOOTPRINT = "FOOTPRINT_KEY"

        fun show(fragment: FragmentBase, footprint: Footprint) {
            MapDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(FOOTPRINT, footprint)
                }
            }
            .show(fragment.childFragmentManager, null)
        }
    }

    @Inject
    lateinit var pinDraw: PinDraw

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogFragment_RoundCorners)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_map, container, false).also { view ->
            val screenSize = requireContext().getScreenSize()

            view.findViewById<FrameLayout>(R.id.mapContainer).apply {
                layoutParams = layoutParams.apply {
                    height = (screenSize.height * 0.8).toInt()
                }
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                (this as BottomSheetDialog).let { d ->
                    val bottomSheet = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

                    val behavior = BottomSheetBehavior.from(bottomSheet)

                    // To show the dialog expanded
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED

                    // To prevent an issue in the map scrolling
                    behavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
                        override fun onStateChanged(p0: View, newState: Int) {
                            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                            }
                        }

                        override fun onSlide(p0: View, p1: Float) {
                        }
                    })
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startLoadingMap()

        closeButton.setOnClickListener { dismiss() }
    }

    override fun onMapReady(map: GoogleMap) {
        // turn off rotation and compass
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isZoomControlsEnabled = true

        with(requireArguments().getParcelable<Footprint>(FOOTPRINT)!!) {
            // Move a camera and zoom
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), Constants.MAP_START_ZOOM))

            // Show a pin
            launch {
                val pinInfo = withContext(dispatchersProvider.calculationsDispatcher) {
                    pinDraw.draw(pinBackgroundColor, pinTextColor, imageContentUri, comment)
                }

                map.addMarker(
                    MarkerOptions()
                    .position(LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.fromBitmap(pinInfo.bitmap))
                    .anchor(pinInfo.spearheadRelativeX, 1.0f))
            }
        }
    }

    override fun inject(key: String) = App.injections.get<MapDialogComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<MapDialogComponent>(key)

    private fun startLoadingMap() {
        val mapFragment = SupportMapFragment.newInstance(GoogleMapOptions())
        childFragmentManager.beginTransaction().add(R.id.mapContainer, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }
}