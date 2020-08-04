package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid

import android.view.ViewGroup
import coil.api.load
import coil.request.RequestDisposable
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import kotlinx.android.synthetic.main.list_item_photo.view.*

class PhotoViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<PhotosListItemEventsProcessor, PhotoListItem>(
    parentView,
    R.layout.list_item_photo
) {
    private var imageDispose: RequestDisposable? = null

    override fun init(listItem: PhotoListItem, listItemEventsProcessor: PhotosListItemEventsProcessor) {
        itemView.setOnClickListener { listItemEventsProcessor.onPhotoClick(listItem.id) }
        imageDispose = itemView.photoImage.load(listItem.imageUri)
    }

    override fun release() {
        itemView.setOnClickListener(null)
        imageDispose?.takeIf { !it.isDisposed } ?.dispose()
    }
}