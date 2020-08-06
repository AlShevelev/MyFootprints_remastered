package com.shevelev.my_footprints_remastered.ui.shared.external_intents.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.BuildConfig
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import java.io.File
import javax.inject.Inject

class CameraHelperImpl
@Inject
constructor(
    private val appContext: Context
) : CameraHelper {
    private companion object {
        private const val REQUEST = 2042
    }

    private lateinit var capturedImageFile: File

    override fun takeCameraPhoto(fragment: Fragment): Boolean {
        val currentImageUri = getCapturedFileUri()

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri)

        if (takePictureIntent.resolveActivity(appContext.packageManager) == null) {
            return false
        }

        fragment.startActivityForResult(takePictureIntent, REQUEST)
        return true
    }

    override fun processCameraPhotoResult(requestCode: Int, resultCode: Int, successAction: (File) -> Unit): Boolean {
        if(resultCode != Activity.RESULT_OK || requestCode != REQUEST) {
            return false
        }

        successAction(capturedImageFile)
        return true
    }

    private fun getCapturedFileUri(): Uri {
        capturedImageFile = File.createTempFile("tmp_", "${IdUtil.generateLongId()}.jpg", appContext.cacheDir)
        return FileProvider.getUriForFile(appContext, BuildConfig.APPLICATION_ID + ".file_provider", capturedImageFile)
    }
}