package com.shevelev.my_footprints_remastered.image_type_detector

import android.content.Context
import android.net.Uri
import com.shevelev.my_footprints_remastered.image_type_detector.signatures.ImageSignature
import java.io.InputStream
import javax.inject.Inject

class ImageTypeDetectorImpl
@Inject
constructor(
    private val signatures: List<ImageSignature>
) : ImageTypeDetector {

    override fun getImageType(context: Context, imageUri: Uri): ImageType {
        context.contentResolver.openInputStream(imageUri).use { inputStream ->
            if(inputStream != null) {
                return getImageType(inputStream)
            }
        }

        return ImageType.UNDEFINED
    }

    override fun getImageType(inputStream: InputStream?): ImageType {
        val buffer = ByteArray(signatures.maxOf { it.size })
        var isRead = false

        if(inputStream != null) {
            inputStream.read(buffer)
            isRead = true
        }

        if(!isRead) {
            return ImageType.UNDEFINED
        }

        signatures.forEach {
            if(it.check(buffer)) {
                return it.type
            }
        }

        return ImageType.UNDEFINED
    }
}