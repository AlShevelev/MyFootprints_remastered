package com.shevelev.my_footprints_remastered.sync.sync_record_repository

import com.shevelev.my_footprints_remastered.common_entities.sync.SyncRecord

interface SyncRecordRepository {
    /**
     * Get all record in the sync log and mark them a processed
     */
    fun getAllAndMark(): List<SyncRecord>

    /**
     * Add the sync log record for a new footprint
     */
    fun addCreateRecord(footprintId: Long)

    /**
     * Add the sync log record for an updated footprint
     */
    fun addUpdateRecord(footprintId: Long, isMetadataUpdated: Boolean, isImageUpdated: Boolean)

    /**
     * Add the sync log record for a deleted footprint
     */
    fun addDeleteRecord(footprintId: Long, googleDriveFileId: String?)

    /**
     * Delete the sync log record by its id (don't mix with footprint id!)
     */
    fun deleteSyncRecord(id: Long)

    /**
     * Remove sync mark from all records
     */
    fun clearMarks()
}