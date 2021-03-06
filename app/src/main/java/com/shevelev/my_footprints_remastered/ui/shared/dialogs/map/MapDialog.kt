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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.GeoPoint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.databinding.DialogMapBinding
import com.shevelev.my_footprints_remastered.databinding.FragmentCropPhotoBinding
import com.shevelev.my_footprints_remastered.ui.shared.Constants
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.utils.location.toMapLocation
import com.shevelev.my_footprints_remastered.utils.resources.getScreenSize
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class MapDialog : BottomSheetDialogFragmentBase(), OnMapReadyCallback {
    companion object {
        private const val ARG_PIN_COLOR = "ARG_PIN_COLOR"
        private const val ARG_IMAGE_FILE = "ARG_IMAGE_FILE"
        private const val ARG_COMMENT = "ARG_COMMENT"
        private const val ARG_LOCATION = "ARG_LOCATION"

        fun show(
            fragment: FragmentBase,
            pinColor: PinColor,
            imageFile: File,
            comment: String?,
            location: GeoPoint
        ) {
            MapDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PIN_COLOR, pinColor)
                    putString(ARG_IMAGE_FILE, imageFile.absolutePath)
                    comment?.let { putString(ARG_COMMENT, it) }
                    putParcelable(ARG_LOCATION, location)
                }
            }
            .show(fragment.childFragmentManager, null)
        }
    }

    @Inject
    lateinit var pinDraw: PinDraw

    private var binding: DialogMapBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogFragment_RoundCorners)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogMapBinding.inflate(inflater, container, false)

        return binding!!.root.also { view ->
            val screenSize = requireContext().getScreenSize()

            view.findViewById<FrameLayout>(R.id.mapContainer).apply {
                layoutParams = layoutParams.apply {
                    height = (screenSize.height * 0.8).toInt()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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

        binding!!.closeButton.setOnClickListener { dismiss() }
    }

    override fun onMapReady(map: GoogleMap) {
        // turn off rotation and compass
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isZoomControlsEnabled = true

        val args = requireArguments()
        val pinColor = args.getParcelable<PinColor>(ARG_PIN_COLOR)!!
        val imageFile = File(requireArguments().getString(ARG_IMAGE_FILE)!!)
        val comment = args.getString(ARG_COMMENT)
        val location = args.getParcelable<GeoPoint>(ARG_LOCATION)!!

        // Move a camera and zoom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location.toMapLocation(), Constants.MAP_START_ZOOM))

        // Show a pin
        launch {
            val pinInfo = withContext(dispatchersProvider.calculationsDispatcher) {
                pinDraw.draw(pinColor.backgroundColor, pinColor.textColor, imageFile, comment)
            }

            map.addMarker(
                MarkerOptions()
                .position(location.toMapLocation())
                .icon(BitmapDescriptorFactory.fromBitmap(pinInfo.bitmap))
                .anchor(pinInfo.spearheadRelativeX, 1.0f))
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