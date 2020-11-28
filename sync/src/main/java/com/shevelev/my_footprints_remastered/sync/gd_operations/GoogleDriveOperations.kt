package com.shevelev.my_footprints_remastered.sync.gd_operations

import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDrive

interface GoogleDriveOperations {
    fun create(footprintMetadata: FootprintMetaGoogleDrive, content: ByteArray, fileName: String): GoogleDriveFileId

    fun read(fileId: GoogleDriveFileId): GoogleDriveFile

    fun readFilesList(): List<GoogleDriveFileId>

    fun updateMetadata(footprintMetadata: FootprintMetaGoogleDrive, fileId: GoogleDriveFileId)

    fun updateContent(content: ByteArray, fileId: GoogleDriveFileId)

    fun updateAll(footprintMetadata: FootprintMetaGoogleDrive, content: ByteArray, fileId: GoogleDriveFileId)

    fun delete(fileId: GoogleDriveFileId)
}