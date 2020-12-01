package com.shevelev.my_footprints_remastered.sync.db_repositories

import com.shevelev.my_footprints_remastered.common_entities.sync.GoogleDriveFileId

interface FirstLoadRecordRepository {
    fun add(ids: List<GoogleDriveFileId>)

    fun get(): List<GoogleDriveFileId>

    fun delete(id: GoogleDriveFileId)
}