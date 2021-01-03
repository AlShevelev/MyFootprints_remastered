package com.shevelev.my_footprints_remastered.storages.files

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface BitmapFilesHelper {
    fun saveUriToFile(uri: Uri, targetFile: File)

    fun saveBitmapToFile(bitmap: Bitmap, targetFile: File)

    fun saveBitesToFile(bytes: ByteArray, targetFile: File)

    fun checkAndCorrectOrientation(file: File): File

    fun createTempFile(): File

    fun getOrCreateImageFile(): File

    fun getOrCreateImageFile(fileName: String): File

    fun copyFile(sourceFile: File, targetFile: File)

    fun deleteFile(file: File)

    fun readFileContent(file: File): ByteArray
}