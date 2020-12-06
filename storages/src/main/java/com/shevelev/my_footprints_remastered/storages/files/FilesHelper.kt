package com.shevelev.my_footprints_remastered.storages.files

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface FilesHelper {
    fun saveUriToFile(uri: Uri, targetFile: File)

    fun saveBitmapToFile(bitmap: Bitmap, targetFile: File, compressRate: Int)

    fun saveBitesToFile(bytes: ByteArray, targetFile: File)

    fun createTempFile(): File

    fun getOrCreateImageFile(): File

    fun getOrCreateImageFile(fileName: String): File

    fun copyFile(sourceFile: File, targetFile: File)

    fun deleteFile(file: File)

    fun readFileContent(file: File): ByteArray
}