package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model.MyWorldMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToOneGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyWorldMapFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: MyWorldMapFragmentModel
) : ViewModelBase<MyWorldMapFragmentModel>(dispatchersProvider, model) {

    private val _footprints = MutableLiveData<FootprintsOnMap>()
    val footprints: LiveData<FootprintsOnMap> = _footprints

    init {
        launch {
            _footprints.value = model.getFootprintsForMap()
        }
    }

    fun onViewCreated() = sendCommand(StartLoadingMap())

    fun onPinClick(id: Long){
        launch {
            model.getIndexById(id)?.let {
                sendCommand(MoveToOneGallery(model.footprints, it))
            }
        }
    }
}