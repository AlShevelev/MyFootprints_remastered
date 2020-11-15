package com.shevelev.my_footprints_remastered.common_entities

import android.location.Location
import java.io.File

data class CreateFootprintInfo(
    val draftImageFile: File,
    val location: GeoPoint,
    val comment: String?,
    val pinColor: PinColor
)