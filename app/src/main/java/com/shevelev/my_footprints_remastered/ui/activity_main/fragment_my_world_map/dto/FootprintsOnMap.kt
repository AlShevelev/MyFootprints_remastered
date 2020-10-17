package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto

data class FootprintsOnMap(
    val footprints: List<FootprintOnMap>,
    val zoomAndLocation: MapZoomAndLocation
)
