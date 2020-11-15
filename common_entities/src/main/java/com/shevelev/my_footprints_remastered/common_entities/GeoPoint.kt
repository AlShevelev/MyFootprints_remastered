package com.shevelev.my_footprints_remastered.common_entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoPoint(
    val latitude: Double,
    val longitude: Double
): Parcelable {
    override fun equals(other: Any?): Boolean =
        (other as? GeoPoint)?.let {
            it.latitude == latitude && it.longitude == longitude
        } ?: false

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }
}