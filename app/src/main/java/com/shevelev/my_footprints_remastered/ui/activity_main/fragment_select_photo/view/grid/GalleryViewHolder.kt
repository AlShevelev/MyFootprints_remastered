package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid

import android.view.ViewGroup
import android.widget.ImageButton
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase

class GalleryViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<PhotosListItemEventsProcessor, GalleryListItem>(
    parentView,
    R.layout.list_item_gallery
) {
    override fun init(listItem: GalleryListItem, listItemEventsProcessor: PhotosListItemEventsProcessor) {
        itemView.findViewById<ImageButton>(R.id.galleryButton).setOnClickListener { listItemEventsProcessor.onGalleryClick() }
    }

    override fun release() {
        itemView.findViewById<ImageButton>(R.id.galleryButton).setOnClickListener(null)
    }
}