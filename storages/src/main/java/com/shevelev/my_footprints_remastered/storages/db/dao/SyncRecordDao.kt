package com.shevelev.my_footprints_remastered.storages.db.dao

import androidx.room.*
import com.shevelev.my_footprints_remastered.storages.db.entities.SyncRecordDb

@Dao
interface SyncRecordDao {
    @Insert
    fun create(footprint: SyncRecordDb)

    @Query("select * from sync_record order by created asc")
    fun readAll(): List<SyncRecordDb>

    @Query("select * from sync_record where footprint_id = :footprintId and sync_in_progress = 0 order by created desc limit 1")
    fun readLast(footprintId: Long): SyncRecordDb?

    @Update
    fun update(footprint: SyncRecordDb)

    @Query("update sync_record set sync_in_progress = :syncInProgress")
    fun updateSyncInProgress(syncInProgress: Int)

    @Query("delete from sync_record")
    fun deleteAll()

    @Query("delete from sync_record where sync_record_id = :recordId")
    fun deleteById(recordId: Long)
}