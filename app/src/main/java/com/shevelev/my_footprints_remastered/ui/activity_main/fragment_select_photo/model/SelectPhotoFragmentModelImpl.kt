package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model

import android.net.Uri
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.photo_items_source.PhotoItemsSource
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid.CameraListItem
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid.GalleryListItem
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid.PhotoListItem
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class SelectPhotoFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val photoItemsSource: PhotoItemsSource,
    private val dataBridge: CreateFootprintFragmentDataBridge
) : ModelBaseImpl(), SelectPhotoFragmentModel {

    override suspend fun getItems(): List<VersionedListItem> =
        withContext(dispatchersProvider.ioDispatcher) {
            val photos = photoItemsSource.getGalleryImagesUrls()

            val result = mutableListOf<VersionedListItem>()
            result.add(CameraListItem(IdUtil.generateLongId(), 0, isFirstItem = true, isLastItem = false))
            result.add(GalleryListItem(IdUtil.generateLongId(), 0, isFirstItem = false, isLastItem = false))
            result.addAll(photos.map {
                PhotoListItem(IdUtil.generateLongId(), 0, isFirstItem = false, isLastItem = false, imageUri = it)
            })
            result
        }

    override fun storeSelectedPhoto(photo: File) = dataBridge.putSelectedPhoto(photo)

    override fun storeSelectedPhoto(photo: Uri) {
        dataBridge.putSelectedPhoto(photo)
    }
}