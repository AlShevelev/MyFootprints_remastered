package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.dto

import android.location.Location
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo

data class PinInfo(
    val location: Location,
    val pin: PinDrawInfo
)