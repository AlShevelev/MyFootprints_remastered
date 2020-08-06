package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.SelectPhotoFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.OpenCamera
import com.shevelev.my_footprints_remastered.ui.view_commands.OpenGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowMessageRes
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class SelectPhotoFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SelectPhotoFragmentModel
) : ViewModelBase<SelectPhotoFragmentModel>(dispatchersProvider, model),
    PhotosListItemEventsProcessor,
    ScreenHeaderBindingCall {

    val title = appContext.getString(R.string.selectPhoto)

    private val _progressVisibility = MutableLiveData<Int>(View.VISIBLE)
    val progressVisibility: LiveData<Int> = _progressVisibility

    private val _listVisibility = MutableLiveData<Int>(View.INVISIBLE)
    val listVisibility: LiveData<Int> = _listVisibility

    private val _items = MutableLiveData<List<VersionedListItem>>()
    val items: LiveData<List<VersionedListItem>> = _items

    init {
        launch {
            try {
                _items.value = model.getItems()
                _listVisibility.value = View.VISIBLE
            } catch (ex: Exception) {
                Timber.e(ex)
                sendCommand(ShowMessageRes(R.string.generalError))
            } finally {
                _progressVisibility.value = View.INVISIBLE
            }
        }
    }

    override fun onBackClick() {
        sendCommand(MoveBack())
    }

    override fun onCameraClick() {
        sendCommand(OpenCamera())
    }

    override fun onGalleryClick() {
        sendCommand(OpenGallery())
    }

    override fun onPhotoClick(photo: Uri) {
        model.storeSelectedPhoto(photo)
        sendCommand(MoveBack())
    }

    fun onCameraImageCaptured(photo: File) {
        model.storeSelectedPhoto(photo)
        sendCommand(MoveBack())
    }

    fun onGalleryImageCaptured(photo: Uri) {
        model.storeSelectedPhoto(photo)
        sendCommand(MoveBack())
    }
}