package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view_model

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model.GalleryPagesFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view.ButtonsBindingCall
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowMapDialog
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.format.toShortDisplayString
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryPagesFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: GalleryPagesFragmentModel
) : ViewModelBase<GalleryPagesFragmentModel>(dispatchersProvider, model),
    ButtonsBindingCall {

    private val _items = MutableLiveData<List<VersionedListItem>>()
    val items: LiveData<List<VersionedListItem>> = _items

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    private val _comment = MutableLiveData<String?>()
    val comment: LiveData<String?> = _comment

    private val _commentVisibility = MutableLiveData(View.INVISIBLE)
    val commentVisibility: LiveData<Int> = _commentVisibility

    private val _createdAt = MutableLiveData<String>()
    val createdAt: LiveData<String> = _createdAt

    init {
        launch {
            _items.value = model.loadItems()
            _currentIndex.value = model.currentIndex
        }
    }

    fun onViewReady() {
        _currentIndex.value = model.currentIndex
    }

    fun onPageSelected(index: Int) {
        model.currentIndex = index

        model.getFootprint(index).let {
            _commentVisibility.value = if(it.comment.isNullOrBlank()) View.INVISIBLE else View.VISIBLE

            _comment.value = it.comment
            _createdAt.value = it.created.toShortDisplayString()
        }
    }

    override fun onMapClick() = sendCommand(ShowMapDialog(model.getFootprint(model.currentIndex)))

    override fun onShareClick() {
        // TODO("Not yet implemented")
    }

    override fun onEditClick() {
        // TODO("Not yet implemented")
    }

    override fun onDeleteClick() {
        // TODO("Not yet implemented")
    }
}