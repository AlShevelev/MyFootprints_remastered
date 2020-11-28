package com.shevelev.my_footprints_remastered.sync.gd_operations

import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDrive

class GoogleDriveFile (
    val id: GoogleDriveFileId,
    val name: String,
    val metadata: FootprintMetaGoogleDrive,
    val content: ByteArray
)