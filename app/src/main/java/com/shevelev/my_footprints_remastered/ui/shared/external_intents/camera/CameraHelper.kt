package com.shevelev.my_footprints_remastered.ui.shared.external_intents.camera

import androidx.fragment.app.Fragment
import java.io.File

interface CameraHelper {
    fun takeCameraPhoto(fragment: Fragment): Boolean

    fun processCameraPhotoResult(requestCode: Int, resultCode: Int, successAction: (File) -> Unit): Boolean
}