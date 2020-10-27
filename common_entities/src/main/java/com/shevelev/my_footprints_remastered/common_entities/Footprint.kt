package com.shevelev.my_footprints_remastered.common_entities

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime

@Parcelize
data class Footprint (
    val id: Long,
    val imageContentUri: Uri,

    /**
     * A name of file with an image for an old API (<29)
     */
    val imageFileName: String?,

    val latitude: Double,
    val longitude: Double,

    val comment: String?,

    val pinTextColor: Int,
    val pinBackgroundColor: Int,

    val created: ZonedDateTime,

    val city: String?,
    val country: String?,
    val isGeoLoaded: Boolean
) : Parcelable