package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.model

import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.model.photo_items_source.PhotoItemsSource
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid.CameraListItem
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid.GalleryListItem
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid.PhotoListItem
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SelectPhotoFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val photoItemsSource: PhotoItemsSource
) : ModelBaseImpl(), SelectPhotoFragmentModel {

    override suspend fun getItems(): List<VersionedListItem> =
        withContext(dispatchersProvider.ioDispatcher) {
            val photos = photoItemsSource.getGalleryImagesUrls()
            Timber.tag("LOAD_ITEMS").d("Photos list received")

            val result = mutableListOf<VersionedListItem>()
            result.add(CameraListItem(IdUtil.generateLongId(), 0, isFirstItem = true, isLastItem = false))
            result.add(GalleryListItem(IdUtil.generateLongId(), 0, isFirstItem = false, isLastItem = false))
            result.addAll(photos.map {
                PhotoListItem(IdUtil.generateLongId(), 0, isFirstItem = false, isLastItem = false, imageUri = it)
            })
            result
        }
}