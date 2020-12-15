package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.view_commands.AskAboutGeolocation
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToSetLocation
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertFootprintViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintFragmentModel
) : CreateFootprintFragmentViewModel(
    dispatchersProvider,
    model) {

    private var locationTrackingJob: Job? = null

    override val title: String = appContext.getString(R.string.createFootprint)

    init {
        launch {
            model.initSharedFootprint()
        }

        if(!model.geolocationProvider.isLocationTrackingEnabled) {
            sendCommand(AskAboutGeolocation)
        }

        startLocationTracking()

        comment.observeForever {
            model.sharedFootprint.comment = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationTracking()
    }

    override fun onMoveToMapClick() = sendCommand(MoveToSetLocation(null, null))

    private fun startLocationTracking() {
        stopLocationTracking()
        locationTrackingJob = launch {
            model.geolocationProvider.startTracking()
        }
    }

    private fun stopLocationTracking() {
        model.geolocationProvider.stopTracking()
        locationTrackingJob?.takeIf { isActive }?.cancel()
        locationTrackingJob = null
    }
}