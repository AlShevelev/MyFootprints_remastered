package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.*
import com.google.maps.android.clustering.ClusterManager
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentMyWorldMapBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.di.MyWorldMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.markers_renderer.PinsRenderer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view_model.MyWorldMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToOneGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import kotlinx.android.synthetic.main.fragment_my_world_map.*
import timber.log.Timber
import javax.inject.Inject

class MyWorldMapFragment : FragmentBaseMVVM<FragmentMyWorldMapBinding, MyWorldMapFragmentViewModel>(), OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var clusterManager: ClusterManager<FootprintOnMap>? = null

    @Inject
    internal lateinit var pinsDraw: PinDraw

    @Inject
    internal lateinit var navigation: MainActivityNavigation

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
            is MoveToOneGallery -> navigation.moveToOneGallery(this, command.footprints,  command.currentIndex)
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

        Timber.tag("GALLERY").d("showFootprints observed")
        viewModel.footprints.observe({viewLifecycleOwner.lifecycle}) { showFootprints(it) }
    }

    private fun startLoadingMap() {
        val mapFragment = SupportMapFragment.newInstance(GoogleMapOptions())
        childFragmentManager.beginTransaction().add(R.id.mapContainer, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    private fun showFootprints(footprints: FootprintsOnMap) {
        Timber.tag("GALLERY").d("showFootprints")
        map?.let { map ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(footprints.centerLocation, footprints.zoomFactor))

            Timber.tag("GALLERY").d("camera moved")
            mapContainer.postDelayed({
                clusterManager = ClusterManager<FootprintOnMap>(requireContext(), map).also { cm ->
                    Timber.tag("GALLERY").d("cluster manager set")

                    cm.renderer = PinsRenderer(requireContext(), pinsDraw, map, cm)

                    // Click on single pin
                    cm.setOnClusterItemClickListener { pin ->
                        Timber.tag("GALLERY").d("on pin click: ${pin.id}")
                        viewModel.onPinClick(pin.id)
                        true
                    }

                    // Process click on a cluster to prevent standard Google map action
                    cm.setOnClusterClickListener {
                        true
                    }

                    // Update clusters on zoom and move
                    map.setOnCameraMoveListener {
                        cm.cluster()
                    }

                    map.setOnMarkerClickListener(cm)

                    footprints.footprints.forEach {
                        cm.addItem(it)
                    }

                    cm.cluster()
                }
            }, 500)
        }
    }
}