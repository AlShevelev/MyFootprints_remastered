package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.pins_cache

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo

interface PinsCache {
    suspend fun get(footprint: FootprintOnMap): PinDrawInfo
}