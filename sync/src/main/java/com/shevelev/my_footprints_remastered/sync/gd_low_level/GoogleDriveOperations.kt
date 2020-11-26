package com.shevelev.my_footprints_remastered.sync.gd_low_level

interface GoogleDriveOperations {
    fun create(metadata: ByteArray, content: ByteArray, fileName: String): GoogleDriveFileId

    fun read(fileId: GoogleDriveFileId): GoogleDriveFile

    fun readFilesList(): List<GoogleDriveFileId>

    fun updateMetadata(metadata: ByteArray, fileId: GoogleDriveFileId)

    fun updateContent(content: ByteArray, fileId: GoogleDriveFileId)

    fun updateAll(metadata: ByteArray, content: ByteArray, fileId: GoogleDriveFileId)

    fun delete(fileId: GoogleDriveFileId)
}