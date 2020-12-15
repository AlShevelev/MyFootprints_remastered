package com.shevelev.my_footprints_remastered.sync.gd_operations

import com.google.api.services.drive.Drive
import com.shevelev.my_footprints_remastered.common_entities.sync.GoogleDriveFileId

data class GoogleDriveService(
    val service: Drive,
    val folder: GoogleDriveFileId
)