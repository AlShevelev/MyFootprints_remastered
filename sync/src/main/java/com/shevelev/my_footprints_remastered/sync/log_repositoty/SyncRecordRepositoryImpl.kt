package com.shevelev.my_footprints_remastered.sync.log_repositoty

import com.shevelev.my_footprints_remastered.common_entities.sync.SyncOperation
import com.shevelev.my_footprints_remastered.common_entities.sync.SyncRecord
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.storages.db.entities.SyncRecordDb
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import javax.inject.Inject

class SyncRecordRepositoryImpl
@Inject
constructor(
    private val db: DbCore
): SyncRecordRepository {
    /**
     * Get all record in the sync log and mark them a processed
     */
    override fun getAllAndMark(): List<SyncRecord> =
        db.runConsistent {
            db.syncRecord.updateSyncInProgress(1)
            db.syncRecord.readAll().map { it.mapToEntity() }
        }

    /**
     * Add the sync log record for a new footprint
     */
    override fun addCreateRecord(footprintId: Long) =
        db.syncRecord.create(createDbRecord(footprintId, SyncOperation.CREATE, null, null))

    /**
     * Add the sync log record for an updated footprint
     */
    override fun addUpdateRecord(footprintId: Long, isMetadataUpdated: Boolean, isImageUpdated: Boolean) {
        db.runConsistent {
            val dbRecord = db.syncRecord.readLast(footprintId)
            if(dbRecord == null) {
                db.syncRecord.create(createDbRecord(footprintId, SyncOperation.UPDATE, isMetadataUpdated, isImageUpdated))
            }
        }
    }

    /**
     * Add the sync log record for a deleted footprint
     */
    override fun addDeleteRecord(footprintId: Long) {
        db.runConsistent {
            val dbRecord = db.syncRecord.readLast(footprintId)

            if(dbRecord == null) {
                db.syncRecord.create(createDbRecord(footprintId, SyncOperation.DELETE, null, null))
            } else {
                when(dbRecord.operation.mapToOperation()) {
                    SyncOperation.CREATE -> db.syncRecord.deleteById(dbRecord.id)
                    SyncOperation.UPDATE -> db.syncRecord.update(dbRecord.copy(operation = SyncOperation.DELETE.mapToDb()))
                    else -> {}
                }
            }
        }
    }

    /**
     * Delete the sync log record by its id (don't mix with footprint id!)
     */
    override fun deleteSyncRecord(id: Long) = db.syncRecord.deleteById(id)

    /**
     * Remove sync mark from all records
     */
    override fun clearMarks() = db.syncRecord.updateSyncInProgress(0)

    /**
     * Delete all records from the sync log
     * For UT only
     */
    fun clear() = db.syncRecord.deleteAll()

    /**
     * Get all records
     * For UT only
     */
    fun getAll() = db.syncRecord.readAll().map { it.mapToEntity() }

    private fun SyncRecordDb.mapToEntity(): SyncRecord =
        SyncRecord(
            id = id,
            footprintId = footprintId,
            operation = operation.mapToOperation(),
            isMetadataUpdated = isMetadataUpdated,
            isImageUpdated = isImageUpdated
        )

    private fun SyncOperation.mapToDb() =
        when(this) {
            SyncOperation.CREATE -> 0
            SyncOperation.UPDATE -> 1
            SyncOperation.DELETE -> 2
        }

    private fun Int.mapToOperation() =
        when(this) {
            0 -> SyncOperation.CREATE
            1 -> SyncOperation.UPDATE
            2 -> SyncOperation.DELETE
            else -> throw UnsupportedOperationException("This operation code is not supported: $this")
        }

    private fun createDbRecord(
        footprintId: Long,
        operation: SyncOperation,
        isMetadataUpdated: Boolean?,
        isImageUpdated: Boolean?) =

        SyncRecordDb(
            id = IdUtil.generateLongId(),
            footprintId = footprintId,
            operation = operation.mapToDb(),
            syncInProgress = 0,
            created = System.nanoTime(),
            isMetadataUpdated = isMetadataUpdated,
            isImageUpdated = isImageUpdated
        )
}