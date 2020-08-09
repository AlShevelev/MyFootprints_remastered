package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface CreateFootprintFragmentDataBridge {
    fun putPhoto(photo: File)

    fun putPhoto(photo: Uri)

    fun putPhoto(photo: Bitmap)

    /**
     * Get and remove selected photo
     */
    fun extractPhotoFile(): File?

    /**
     * Get and remove selected photo
     */
    fun extractPhotoUri(): Uri?

    /**
     * Get and remove selected photo
     */
    fun extractPhotoBitmap(): Bitmap?
}