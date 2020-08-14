package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets.PhotoContainerBindingCall
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets.PhotoContainerState
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToCropPhoto
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToSelectPhoto
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CreateFootprintFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintFragmentModel
) : ViewModelBase<CreateFootprintFragmentModel>(dispatchersProvider, model),
    PhotoContainerBindingCall,
    ScreenHeaderBindingCall {

    val title = appContext.getString(R.string.createFootprint)

    private val _containerState = MutableLiveData<PhotoContainerState>(PhotoContainerState.Initial)
    val containerState: LiveData<PhotoContainerState> = _containerState

    override fun onBackClick() {
        sendCommand(MoveBack())
    }

    override fun onAddPhotoClick() {
        sendCommand(MoveToSelectPhoto())
    }

    override fun onClearPhotoClick() {
        launch {
            model.clearPhoto()
            _containerState.value = PhotoContainerState.Initial
        }
    }

    override fun onCropPhotoClick() {
        model.photoImage?.let {
            sendCommand(MoveToCropPhoto(it))
        }
    }

    override fun onFilterPhotoClick() {
        Timber.d("Filter")
    }

    fun onActive() {
        launch {
            model.processNewPhotoSelected { state ->
                when(state) {
                    is SelectedPhotoLoadingState.Ready -> _containerState.value = PhotoContainerState.Ready(state.file)
                    is SelectedPhotoLoadingState.Loading -> _containerState.value = PhotoContainerState.Loading
                }
            }
        }
    }
}