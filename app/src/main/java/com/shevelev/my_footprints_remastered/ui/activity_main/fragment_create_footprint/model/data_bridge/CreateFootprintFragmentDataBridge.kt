package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge

import android.net.Uri
import java.io.File

interface CreateFootprintFragmentDataBridge {
    fun putSelectedPhoto(photo: File)

    fun putSelectedPhoto(photo: Uri)

    /**
     * Get and remove selected photo
     */
    fun extractSelectedPhotoFile(): File?
    /**
     * Get and remove selected photo
     */
    fun extractSelectedPhotoUri(): Uri?
}