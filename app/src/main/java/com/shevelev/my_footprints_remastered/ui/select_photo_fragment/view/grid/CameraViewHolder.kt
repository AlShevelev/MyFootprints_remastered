package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid

import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model.PhotosListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import kotlinx.android.synthetic.main.list_item_camera.view.*

class CameraViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<PhotosListItemEventsProcessor, CameraListItem>(
    parentView,
    R.layout.list_item_camera
) {
    override fun init(listItem: CameraListItem, listItemEventsProcessor: PhotosListItemEventsProcessor) {
        itemView.cameraButton.setOnClickListener { listItemEventsProcessor.onCameraClick() }
    }

    override fun release() {
        itemView.cameraButton.setOnClickListener(null)
    }
}