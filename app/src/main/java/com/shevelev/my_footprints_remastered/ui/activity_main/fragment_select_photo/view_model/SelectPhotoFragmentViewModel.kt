package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.SelectPhotoFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.ShowMessageResCommand
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SelectPhotoFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SelectPhotoFragmentModel
) : ViewModelBase<SelectPhotoFragmentModel>(dispatchersProvider, model),
    PhotosListItemEventsProcessor {

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
                Timber.tag("LOAD_ITEMS").d("Start")
                _items.value = model.getItems()
                Timber.tag("LOAD_ITEMS").d("Completed")
                _listVisibility.value = View.VISIBLE
            } catch (ex: Exception) {
                Timber.e(ex)
                _command.value = ShowMessageResCommand(R.string.generalError)
            } finally {
                _progressVisibility.value = View.INVISIBLE
                Timber.tag("LOAD_ITEMS").d("Progress removed")
            }
        }
    }

    fun onBackButtonClick() {
        _command.value = MoveBack()
    }

    override fun onCameraClick() {
        //TODO("Not yet implemented")
    }

    override fun onGalleryClick() {
        //TODO("Not yet implemented")
    }

    override fun onPhotoClick(id: Long) {
        //TODO("Not yet implemented")
    }
}