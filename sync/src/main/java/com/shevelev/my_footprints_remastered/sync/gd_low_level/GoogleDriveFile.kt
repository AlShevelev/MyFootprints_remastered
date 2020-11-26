package com.shevelev.my_footprints_remastered.sync.gd_low_level

class GoogleDriveFile (
    val id: GoogleDriveFileId,
    val name: String,
    val metadata: ByteArray,
    val content: ByteArray
)