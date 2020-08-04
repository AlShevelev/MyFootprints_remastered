package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.model.photo_items_source

import android.net.Uri

interface PhotoItemsSource {
    /**
     * Returns list of images in the phone gallery, sorted by lastItemDate in descending order
     */
    fun getGalleryImagesUrls(): List<Uri>
}