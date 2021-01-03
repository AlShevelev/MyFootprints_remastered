package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid

import android.view.ViewGroup
import android.widget.ImageView
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.glide.GlideTarget
import com.shevelev.my_footprints_remastered.ui.shared.glide.clear
import com.shevelev.my_footprints_remastered.ui.shared.glide.load
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase

class PhotoViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<PhotosListItemEventsProcessor, PhotoListItem>(
    parentView,
    R.layout.list_item_photo
) {
    private var imageDispose: GlideTarget? = null

    override fun init(listItem: PhotoListItem, listItemEventsProcessor: PhotosListItemEventsProcessor) {
        itemView.setOnClickListener { listItemEventsProcessor.onPhotoClick(listItem.imageUri) }
        imageDispose = itemView
            .findViewById<ImageView>(R.id.photoImage)
            .load(listItem.imageUri)
    }

    override fun release() {
        itemView.setOnClickListener(null)
        imageDispose?.clear(itemView.context)
    }
}