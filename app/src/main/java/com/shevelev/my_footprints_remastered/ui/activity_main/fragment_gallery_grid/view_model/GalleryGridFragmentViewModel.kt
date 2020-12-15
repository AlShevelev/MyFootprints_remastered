package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model.GalleryGridFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToOneGallery
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryGridFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: GalleryGridFragmentModel
) : ViewModelBase<GalleryGridFragmentModel>(dispatchersProvider, model),
    ScreenHeaderBindingCall,
    FootprintListItemEventsProcessor {

    val title = appContext.getString(R.string.gallery)

    private val _progressVisibility = MutableLiveData(View.VISIBLE)
    val progressVisibility: LiveData<Int> = _progressVisibility

    private val _listVisibility = MutableLiveData(View.INVISIBLE)
    val listVisibility: LiveData<Int> = _listVisibility

    private val _items = MutableLiveData<List<VersionedListItem>>()
    val items: LiveData<List<VersionedListItem>> = _items

    init {
        launch {
            _items.value = model.loadItems()
            _progressVisibility.value = View.INVISIBLE
            _listVisibility.value = View.VISIBLE
        }

        launch {
            model.updateFootprintData.data.collect { footprint ->
                footprint?.let {
                    model.updateFootprint(footprint)?.let { _items.value = it }
                }
            }
        }

        launch {
            model.deleteFootprintData.data.collect { deletedFootprintInfo ->
                deletedFootprintInfo?.let {
                    _items.value = model.deleteFootprint(it.deletedFootprintId)
                }
            }
        }
    }

    override fun onBackClick() = sendCommand(MoveBack)

    override fun onFootprintClick(id: Long) {
        model.items.let { items ->
            sendCommand(MoveToOneGallery(items, items.indexOfFirst { it.id == id }))
        }
    }
}