package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModel
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToCreateFootprint
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToGridGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToMyWorld
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.resources.getStringFormatted
import com.shevelev.my_footprints_remastered.utils.strings.toUpper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TitleFragmentViewModel
@Inject
constructor(
    private val appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: TitleFragmentModel
) : ViewModelBase<TitleFragmentModel>(dispatchersProvider, model) {

    private val _total = MutableLiveData(getTotalFootprintsText(0))
    val total = _total as LiveData<String>

    private val _lastFootprintUri = MutableLiveData<Uri>()
    val lastFootprintUri = _lastFootprintUri as LiveData<Uri>

    private val _loadingVisibility = MutableLiveData(View.VISIBLE)
    val loadingVisibility: LiveData<Int> = _loadingVisibility

    private val _lastFootprintVisibility = MutableLiveData(View.INVISIBLE)
    val lastFootprintVisibility: LiveData<Int> = _lastFootprintVisibility

    private val _galleryEnabled = MutableLiveData(false)
    val galleryEnabled: LiveData<Boolean> = _galleryEnabled

    init {
        launch {
            model.lastFootprintData.data.collect {
                it?.let { lastFootprintInfo ->
                    _total.value = getTotalFootprintsText(lastFootprintInfo.totalFootprints)
                    _galleryEnabled.value = it.totalFootprints > 0

                    _lastFootprintUri.value = getLastFootprintUri(lastFootprintInfo.lastFootprintUri)
                    _loadingVisibility.value = View.INVISIBLE
                    _lastFootprintVisibility.value = View.VISIBLE

                    model.lastFootprintId = lastFootprintInfo.lastFootprintId
                }
            }
        }

        launch {
            model.updateFootprintData.data.collect {
                it?.let { updatedFootprint ->
                    if(updatedFootprint.id == model.lastFootprintId) {
                        _lastFootprintUri.value = updatedFootprint.imageContentUri
                    }
                }
            }
        }

        launch {
            model.lastFootprintData.init()
        }
    }

    fun onNewFootprintClick() = sendCommand(MoveToCreateFootprint(null))

    fun onGalleryClick() = sendCommand(MoveToGridGallery())

    fun onMyWorldClick() = sendCommand(MoveToMyWorld())

    private fun getTotalFootprintsText(total: Int) = appContext.getStringFormatted(R.string.totalFootprints, total).toUpper()

    private fun getLastFootprintUri(uri: Uri?): Uri =
        uri ?: Uri.parse("android.resource://${appContext.packageName}/drawable/img_title_empty")
}