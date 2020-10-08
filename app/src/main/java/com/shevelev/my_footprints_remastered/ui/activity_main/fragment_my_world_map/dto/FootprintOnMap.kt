package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.shevelev.my_footprints_remastered.common_entities.PinColor

data class FootprintOnMap(
    val id: Long,
    val imageContentUri: Uri,
    val location: LatLng,
    val comment: String?,
    val pinColor: PinColor
)