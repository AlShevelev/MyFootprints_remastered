package com.shevelev.my_footprints_remastered.storages.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "first_load_record")
data class FirstLoadRecordDb (
    @PrimaryKey
    @ColumnInfo(name = "gd_file_id")
    val googleDriveFileId: String
)
