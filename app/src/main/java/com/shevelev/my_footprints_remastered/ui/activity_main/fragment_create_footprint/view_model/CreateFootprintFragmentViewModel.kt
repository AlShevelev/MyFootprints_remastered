package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.ButtonsBindingCall
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets.PhotoContainerBindingCall
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets.PhotoContainerState
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class CreateFootprintFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintFragmentModel
) : ViewModelBase<CreateFootprintFragmentModel>(dispatchersProvider, model),
    PhotoContainerBindingCall,
    ScreenHeaderBindingCall,
    ButtonsBindingCall {

    val title = appContext.getString(R.string.createFootprint)

    private val _containerState = MutableLiveData<PhotoContainerState>(PhotoContainerState.Initial)
    val containerState: LiveData<PhotoContainerState> = _containerState

    val comment = MutableLiveData<String>()

    private val _saveEnabled = MutableLiveData(model.canSave)
    val saveEnabled:LiveData<Boolean> = _saveEnabled

    private var locationTrackingJob: Job? = null

    init {
        model.initSharedFootprint()

        if(!model.geolocationProvider.isLocationTrackingEnabled) {
            sendCommand(AskAboutGeolocation())
        }

        startLocationTracking()

        comment.observeForever {
            model.sharedFootprint.comment = it
        }
    }

    override fun onBackClick() = sendCommand(MoveBack())

    override fun onAddPhotoClick() = sendCommand(MoveToSelectPhoto())

    override fun onClearPhotoClick() {
        launch {
            model.clearPhoto()
            _containerState.value = PhotoContainerState.Initial
            _saveEnabled.value = model.canSave
        }
    }

    override fun onCropPhotoClick() {
        model.sharedFootprint.image?.let {
            sendCommand(MoveToCropPhoto(it))
        }
    }

    override fun onFilterPhotoClick() {
        model.sharedFootprint.image?.let {
            sendCommand(MoveToEditPhoto(it))
        }
    }

    fun onViewCreated() {
        launch {
            model.processNewPhotoSelected { state ->
                when(state) {
                    is SelectedPhotoLoadingState.Ready -> _containerState.value = PhotoContainerState.Ready(state.file)
                    is SelectedPhotoLoadingState.Loading -> _containerState.value = PhotoContainerState.Loading
                }
            }
            _saveEnabled.value = model.canSave
        }
    }

    fun onGotoLocationSettingsSelected() = sendCommand(OpenLocationSettings())

    override fun onCleared() {
        super.onCleared()
        stopLocationTracking()
    }

    override fun onMoveToMapClick() = sendCommand(MoveToSetLocation())

    override fun onSaveClick() {
        launch {
            try {
                model.save()
            } catch (ex: Exception) {
                Timber.e(ex)
                sendCommand(ShowMessageRes(R.string.saveFootprintError))
            }
        }
    }

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