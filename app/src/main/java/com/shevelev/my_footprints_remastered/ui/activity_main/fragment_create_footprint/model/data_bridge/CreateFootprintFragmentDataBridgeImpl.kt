package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge

import android.graphics.Bitmap
import android.net.Uri
import com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.FragmentsDataPushBase
import java.io.File
import javax.inject.Inject

class CreateFootprintFragmentDataBridgeImpl
@Inject
constructor() : FragmentsDataPushBase(), CreateFootprintFragmentDataBridge {
    companion object {
        private const val PHOTO_FILE = 1
        private const val PHOTO_URI = 2
        private const val PHOTO_BITMAP = 3
    }

    override fun putPhoto(photo: File) = put(PHOTO_FILE, photo)

    override fun putPhoto(photo: Uri) = put(PHOTO_URI, photo)

    override fun putPhoto(photo: Bitmap) = put(PHOTO_BITMAP, photo)

    /**
     * Get and remove selected photo
     */
    override fun extractPhotoFile(): File? = remove(PHOTO_FILE) as? File

    /**
     * Get and remove selected photo
     */
    override fun extractPhotoUri(): Uri? = remove(PHOTO_URI) as? Uri

    /**
     * Get and remove selected photo
     */
    override fun extractPhotoBitmap(): Bitmap? = remove(PHOTO_BITMAP) as? Bitmap
}