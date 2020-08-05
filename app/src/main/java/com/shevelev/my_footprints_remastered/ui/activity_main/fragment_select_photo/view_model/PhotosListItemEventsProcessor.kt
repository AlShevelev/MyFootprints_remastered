package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model

import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListItemEventsProcessor

interface PhotosListItemEventsProcessor: ListItemEventsProcessor {
    fun onCameraClick()

    fun onGalleryClick()

    fun onPhotoClick(id: Long)
}