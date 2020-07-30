package com.shevelev.my_footprints_remastered.storages.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "footprint")
data class FootprintDb(
    @PrimaryKey
    @ColumnInfo(name = "footprint_id")
    val id: Long,

    @ColumnInfo(name = "file_name")
    val fileName: String,

    @ColumnInfo(name = "created", typeAffinity = ColumnInfo.BLOB)
    val created: ZonedDateTime,

    @ColumnInfo(name = "created_sort", index = true)
    val createdSort: Long
)
