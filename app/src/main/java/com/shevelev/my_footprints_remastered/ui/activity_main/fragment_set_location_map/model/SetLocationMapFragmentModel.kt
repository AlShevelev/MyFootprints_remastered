package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model

import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinInfo

interface SetLocationMapFragmentModel : ModelBase {
    val locationProvider: GeolocationProviderData

    suspend fun getPinInfo(): PinInfo
}