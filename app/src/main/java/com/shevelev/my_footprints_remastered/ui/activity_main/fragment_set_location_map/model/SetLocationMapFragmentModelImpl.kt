package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetLocationMapFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    override val locationProvider: GeolocationProviderData,
    private val pinDraw: PinDraw,
    private val sharedFootprint: SharedFootprint
) : ModelBaseImpl(),
    SetLocationMapFragmentModel {

    override var pinTextColor: Int
        get() = sharedFootprint.pinTextColor
        set(value) { sharedFootprint.pinTextColor = value }

    override var pinBackgroundColor: Int
        get() = sharedFootprint.pinBackgroundColor
        set(value) { sharedFootprint.pinBackgroundColor = value }

    override suspend fun getPinInfo(): PinDrawInfo =
        withContext(dispatchersProvider.calculationsDispatcher) {
            pinDraw.draw(
                sharedFootprint.pinBackgroundColor,
                sharedFootprint.pinTextColor,
                sharedFootprint.image,
                sharedFootprint.comment
            )
        }
}