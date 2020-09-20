package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model

import android.location.Location
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetLocationMapFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val locationProvider: GeolocationProviderData,
    private val pinDraw: PinDraw,
    private val sharedFootprint: SharedFootprint
) : ModelBaseImpl(),
    SetLocationMapFragmentModel {

    override var pinColor: PinColor
        get() = sharedFootprint.pinColor
        set(value) { sharedFootprint.pinColor = value }

    override var manualLocation: Location?
        get() = sharedFootprint.manualSelectedLocation
        set(value) { sharedFootprint.manualSelectedLocation = value }

    override var pinInfo: PinDrawInfo? = null
        private set

    override val lastLocation: Location
        get() = manualLocation ?: locationProvider.lastLocation

    override val lastLocationFlow: Flow<Location>
        get() = locationProvider.lastLocationFlow

    override suspend fun updatePinInfo(): PinDrawInfo =
        withContext(dispatchersProvider.calculationsDispatcher) {
            pinDraw.draw(
                sharedFootprint.pinColor.backgroundColor,
                sharedFootprint.pinColor.textColor,
                sharedFootprint.image,
                sharedFootprint.comment
            )
        }.also { pinInfo = it }
}