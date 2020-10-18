package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.MapZoomAndLocation
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model.MyWorldMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToOneGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyWorldMapFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: MyWorldMapFragmentModel
) : ViewModelBase<MyWorldMapFragmentModel>(dispatchersProvider, model) {

    private var manualZoomAndLocation: MapZoomAndLocation? = null

    private val _footprints = MutableLiveData<List<FootprintOnMap>>()
    val footprints: LiveData<List<FootprintOnMap>> = _footprints

    private val _zoomAndLocation = MutableLiveData<MapZoomAndLocation>()
    val zoomAndLocation: LiveData<MapZoomAndLocation> = _zoomAndLocation

    init {
        launch {
            val footprintsForMap = model.getFootprintsForMap()
            _zoomAndLocation.value = footprintsForMap.zoomAndLocation
            _footprints.value = footprintsForMap.footprints
        }

        launch {
            model.updateFootprintData.data.collect { footprint ->
                footprint?.let {
                    model.updateFootprint(footprint)?.let { _footprints.value = it }
                }
            }
        }
    }

    fun onViewCreated() {
        manualZoomAndLocation?.let {
            _zoomAndLocation.value = it
        }
        sendCommand(StartLoadingMap())
    }

    fun onPinClick(id: Long){
        launch {
            model.getIndexById(id)?.let {
                sendCommand(MoveToOneGallery(model.footprints, it))
            }
        }
    }

    fun storeManualZoomAndLocation(zoomAndLocation: MapZoomAndLocation) {
        manualZoomAndLocation = zoomAndLocation
    }
}