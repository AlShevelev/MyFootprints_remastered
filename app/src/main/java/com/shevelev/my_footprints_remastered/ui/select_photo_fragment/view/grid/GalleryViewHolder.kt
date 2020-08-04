package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid

import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import kotlinx.android.synthetic.main.list_item_gallery.view.*

class GalleryViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<PhotosListItemEventsProcessor, GalleryListItem>(
    parentView,
    R.layout.list_item_gallery
) {
    override fun init(listItem: GalleryListItem, listItemEventsProcessor: PhotosListItemEventsProcessor) {
        itemView.galleryButton.setOnClickListener { listItemEventsProcessor.onGalleryClick() }
    }

    override fun release() {
        itemView.galleryButton.setOnClickListener(null)
    }
}