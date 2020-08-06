package com.shevelev.my_footprints_remastered.ui.shared.external_intents.gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import javax.inject.Inject

class GalleryHelperImpl
@Inject
constructor(
    private val appContext: Context
) : GalleryHelper {
    companion object {
        private const val REQUEST = 1761
    }

    override fun takePhoto(fragment: Fragment): Boolean {
        val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (takePictureIntent.resolveActivity(appContext.packageManager) == null) {
            return false
        }

        fragment.startActivityForResult(takePictureIntent, REQUEST)
        return true

    }

    override fun processCameraPhotoResult(requestCode: Int, resultCode: Int, data: Intent?, successAction: (Uri) -> Unit): Boolean {
        if(resultCode != Activity.RESULT_OK || requestCode != REQUEST) {
            return false
        }

        if(data == null) {
            return false
        }

        successAction(data.data as Uri)
        return true
    }
}