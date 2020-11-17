package com.shevelev.my_footprints_remastered.storages.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_record")
data class SyncRecordDb (
    @PrimaryKey
    @ColumnInfo(name = "sync_record_id")
    val id: Long,

    @ColumnInfo(name = "footprint_id", index = true)
    val footprintId: Long,

    @ColumnInfo(name = "operation")
    val operation: Int,

    @ColumnInfo(name = "sync_in_progress")
    val syncInProgress: Int,

    @ColumnInfo(name = "created", index = true)
    val created: Long
)
