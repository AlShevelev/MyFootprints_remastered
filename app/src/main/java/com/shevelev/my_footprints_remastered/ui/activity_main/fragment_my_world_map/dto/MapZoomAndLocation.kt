package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto

import com.google.android.gms.maps.model.LatLng

data class MapZoomAndLocation(
    val zoomFactor: Float,
    val centerLocation: LatLng
)