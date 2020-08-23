package com.shevelev.my_footprints_remastered.storages.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "last_location")
data class LastLocationDb(
    @PrimaryKey
    @ColumnInfo(name = "last_location_id")
    val id: Long,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "time")
    val time: Date,

    @ColumnInfo(name = "provider")
    val provider: String
)