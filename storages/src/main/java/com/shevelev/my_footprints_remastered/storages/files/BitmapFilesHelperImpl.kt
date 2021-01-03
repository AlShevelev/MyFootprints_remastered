package com.shevelev.my_footprints_remastered.storages.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import java.io.File
import javax.inject.Inject

class BitmapFilesHelperImpl
@Inject
constructor(
    private val appContext: Context
) : BitmapFilesHelper {

    companion object {
        private const val DEFAULT_COMPRESS_RATE = 75
    }

    override fun saveUriToFile(uri: Uri, targetFile: File) {
        appContext.contentResolver.openInputStream(uri).use { input ->
            val markSupported = input!!.markSupported()

            val exifOrientation = if(markSupported) {
                input.mark(Int.MAX_VALUE)
                ExifInterface(input).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            } else {
                ExifInterface.ORIENTATION_UNDEFINED
            }

            if(markSupported) {
                input.reset()
            }

            if(!needToRotate(exifOrientation)) {
                targetFile.outputStream().use { fileOut ->
                    input.copyTo(fileOut)
                }
            } else {
                val bitmap = rotateBitmap(BitmapFactory.decodeStream(input), exifOrientation)
                saveBitmapToFile(bitmap, targetFile)
            }
        }
    }

    override fun saveBitmapToFile(bitmap: Bitmap, targetFile: File) {
        targetFile.outputStream().use { fileOut ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, DEFAULT_COMPRESS_RATE, fileOut)
        }
    }

    override fun saveBitesToFile(bytes: ByteArray, targetFile: File) {
        targetFile.outputStream().use { fileOut ->
            fileOut.write(bytes)
        }
    }

    override fun checkAndCorrectOrientation(file: File): File {
        val exifOrientation = ExifInterface(file)
            .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

        if(needToRotate(exifOrientation)) {
            saveBitmapToFile(rotateBitmap(BitmapFactory.decodeFile(file.absolutePath), exifOrientation), file)
        }
        return file
    }

    override fun createTempFile(): File = File.createTempFile("tmp_", createFileName(), appContext.cacheDir)

    override fun getOrCreateImageFile(): File = getOrCreateImageFile(createFileName())

    override fun getOrCreateImageFile(fileName: String): File {
        val dir = File(appContext.filesDir, "photos")
        if(!dir.exists()) {
            dir.mkdir()
        }

        return File(dir, fileName)
    }

    override fun copyFile(sourceFile: File, targetFile: File) {
        sourceFile.copyTo(targetFile, true)
    }

    override fun deleteFile(file: File) {
        file.delete()
    }

    override fun readFileContent(file: File): ByteArray = file.readBytes()

    private fun createFileName() = "${IdUtil.generateLongId()}.jpg"

    private fun rotateBitmap(source: Bitmap, exifOrientation: Int): Bitmap {
        val rotateAngle = when(exifOrientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 ->180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }

        if(rotateAngle == 0) {
            return source
        }

        val matrix = Matrix()
        matrix.postRotate(rotateAngle.toFloat())

        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    private fun needToRotate(exifOrientation: Int) =
        exifOrientation == ExifInterface.ORIENTATION_ROTATE_90 ||
        exifOrientation == ExifInterface.ORIENTATION_ROTATE_180 ||
        exifOrientation == ExifInterface.ORIENTATION_ROTATE_270

}