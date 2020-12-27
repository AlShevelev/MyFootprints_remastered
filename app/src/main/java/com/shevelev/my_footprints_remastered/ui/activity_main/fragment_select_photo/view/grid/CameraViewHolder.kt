package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid

import android.view.ViewGroup
import android.widget.ImageButton
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase

class CameraViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<PhotosListItemEventsProcessor, CameraListItem>(
    parentView,
    R.layout.list_item_camera
) {
    override fun init(listItem: CameraListItem, listItemEventsProcessor: PhotosListItemEventsProcessor) {
        itemView.findViewById<ImageButton>(R.id.cameraButton).setOnClickListener { listItemEventsProcessor.onCameraClick() }
    }

    override fun release() {
        itemView.findViewById<ImageButton>(R.id.cameraButton).setOnClickListener(null)
    }
}