package com.shevelev.my_footprints_remastered.sync.db_repositories

import com.shevelev.my_footprints_remastered.common_entities.sync.GoogleDriveFileId
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.storages.db.entities.FirstLoadRecordDb
import javax.inject.Inject

class FirstLoadRecordRepositoryImpl
@Inject
constructor(
    private val db: DbCore
) : FirstLoadRecordRepository {
    override fun add(ids: List<GoogleDriveFileId>)  = db.firstLoadRecord.create(ids.map { FirstLoadRecordDb(it.id) })

    override fun get(): List<GoogleDriveFileId> = db.firstLoadRecord.readAll().map { GoogleDriveFileId(it.googleDriveFileId) }

    override fun delete(id: GoogleDriveFileId) = db.firstLoadRecord.delete(id.id)
}