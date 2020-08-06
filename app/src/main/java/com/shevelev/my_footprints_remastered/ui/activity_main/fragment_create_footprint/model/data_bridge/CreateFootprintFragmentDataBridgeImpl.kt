package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge

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
    }

    override fun putSelectedPhoto(photo: File) = put(PHOTO_FILE, photo)

    override fun putSelectedPhoto(photo: Uri) = put(PHOTO_URI, photo)

    /**
     * Get and remove selected photo
     */
    override fun extractSelectedPhotoFile(): File? = remove(PHOTO_FILE) as File?

    /**
     * Get and remove selected photo
     */
    override fun extractSelectedPhotoUri(): Uri? = remove(PHOTO_URI) as Uri?
}