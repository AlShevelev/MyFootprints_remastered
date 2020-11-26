package com.shevelev.my_footprints_remastered.common_entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime

@Parcelize
data class Footprint (
    val id: Long,

    val imageFileName: String,

    val location: GeoPoint,

    val comment: String?,

    val pinColor: PinColor,

    val created: ZonedDateTime,

    val city: String?,
    val country: String?,
    val isGeoLoaded: Boolean,

    val googleDriveFileId: String?
) : Parcelable