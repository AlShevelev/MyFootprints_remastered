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

    @ColumnInfo(name = "image_content_uri")
    val imageContentUri: String,

    @ColumnInfo(name = "image_file_name")
    val imageFileName: String?,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "comment")
    val comment: String?,

    @ColumnInfo(name = "pin_text_color")
    val pinTextColor: Int,

    @ColumnInfo(name = "pin_background_color")
    val pinBackgroundColor: Int,

    @ColumnInfo(name = "created", typeAffinity = ColumnInfo.BLOB)
    val created: ZonedDateTime,

    @ColumnInfo(name = "created_sort", index = true)
    val createdSort: Long,

    @ColumnInfo(name = "city")
    val city: String?,

    @ColumnInfo(name = "country")
    val country: String?,

    @ColumnInfo(name = "is_geo_loaded")
    val isGeoLoaded: Boolean
)
