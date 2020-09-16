package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view_model

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model.SetLocationMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.InitMapUserData
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowMessageRes
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetLocationMapFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: SetLocationMapFragmentModel
) : ViewModelBase<SetLocationMapFragmentModel>(dispatchersProvider, model) {

    private val startZoom = 18f

    init {
        sendCommand(StartLoadingMap())

        launch {
            model.locationProvider.lastLocationFlow.collect {
                // Update pin location if it was not located manually
            }
        }
    }

    fun mapLoaded() {
        launch {
            try {
                sendCommand(InitMapUserData(startZoom, model.locationProvider.lastLocation, model.getPinInfo()))
            } catch (ex: Exception) {
                sendCommand(ShowMessageRes(R.string.generalError))
            }
        }
    }
}