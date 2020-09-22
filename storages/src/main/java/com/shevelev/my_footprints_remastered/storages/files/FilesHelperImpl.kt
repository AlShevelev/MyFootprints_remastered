package com.shevelev.my_footprints_remastered.storages.files

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import java.io.File
import javax.inject.Inject

class FilesHelperImpl
@Inject
constructor(
    private val appContext: Context
) : FilesHelper {

    override fun saveUriToFile(uri: Uri, targetFile: File) {
        appContext.contentResolver.openInputStream(uri).use { input ->
            targetFile.outputStream().use { fileOut ->
                input?.copyTo(fileOut)
            }
        }
    }

    override fun saveBitmapToFile(bitmap: Bitmap, targetFile: File, compressRate: Int) {
        targetFile.outputStream().use { fileOut ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressRate, fileOut)
        }
    }

    override fun createTempFile(): File = File.createTempFile("tmp_", createFileName(), appContext.cacheDir)

    override fun createImageFile(appName: String): File {
        val dir = File(appContext.externalMediaDirs[0], appName)
        if(!dir.exists()) {
            dir.mkdir()
        }

        return File(dir, createFileName())
    }

    override fun copyFile(sourceFile: File, targetFile: File) {
        sourceFile.copyTo(targetFile, true)
    }

    override fun deleteFile(file: File) {
        file.delete()
    }

    private fun createFileName() = "${IdUtil.generateLongId()}.jpg"
}