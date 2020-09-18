package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model

import android.location.Location
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo
import kotlinx.coroutines.flow.Flow

interface SetLocationMapFragmentModel : ModelBase {
    var pinTextColor: Int

    var pinBackgroundColor: Int

    var manualLocation: Location?

    val pinInfo: PinDrawInfo?

    val lastLocation: Location

    val lastLocationFlow: Flow<Location>

    suspend fun updatePinInfo(): PinDrawInfo
}