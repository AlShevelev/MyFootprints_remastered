package com.shevelev.my_footprints_remastered.common_entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PinColor (
    /**
     * Text color represented as Int value
     */
    val textColor: Int,

    /**
     * Background color represented as Int value
     */
    val backgroundColor: Int
): Parcelable {
    override fun equals(other: Any?): Boolean =
        (other as? PinColor)?.let {
            it.textColor == textColor && it.backgroundColor == backgroundColor
        } ?: false

    override fun hashCode(): Int {
        var result = textColor
        result = 31 * result + backgroundColor
        return result
    }
}