package com.shevelev.my_footprints_remastered.common_entities

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.ZonedDateTime

@Parcelize
data class Footprint (
    val id: Long,
    val imageContentUri: Uri,

    val latitude: Double,
    val longitude: Double,

    val comment: String?,

    val pinTextColor: Int,
    val pinBackgroundColor: Int,

    val created: ZonedDateTime
) : Parcelable