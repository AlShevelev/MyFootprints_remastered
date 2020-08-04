package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid

import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListAdapterBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

class PhotoGridAdapter(
    listItemEventsProcessor: PhotosListItemEventsProcessor
) : VersionedListAdapterBase<PhotosListItemEventsProcessor>(listItemEventsProcessor, null) {

    private companion object {
        const val CAMERA = 0
        const val GALLERY = 1
        const val PHOTO = 2
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<PhotosListItemEventsProcessor, VersionedListItem> =
        when(viewType) {
            CAMERA -> CameraViewHolder(parent) as  ViewHolderBase<PhotosListItemEventsProcessor, VersionedListItem>
            GALLERY -> GalleryViewHolder(parent) as  ViewHolderBase<PhotosListItemEventsProcessor, VersionedListItem>
            PHOTO -> PhotoViewHolder(parent) as  ViewHolderBase<PhotosListItemEventsProcessor, VersionedListItem>
            else -> throw UnsupportedOperationException("This type of item is not supported")
        }

    override fun getItemViewType(position: Int): Int =
        when(items[position]) {
            is CameraListItem -> CAMERA
            is GalleryListItem -> GALLERY
            is PhotoListItem -> PHOTO
            else -> throw UnsupportedOperationException("This type of item is not supported")
        }
}