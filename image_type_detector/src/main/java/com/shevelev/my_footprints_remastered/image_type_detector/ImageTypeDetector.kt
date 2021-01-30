package com.shevelev.my_footprints_remastered.image_type_detector

import android.content.Context
import android.net.Uri
import java.io.InputStream

interface ImageTypeDetector {
    fun getImageType(context: Context, imageUri: Uri): ImageType

    fun getImageType(inputStream: InputStream?): ImageType
}