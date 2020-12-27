package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface GeolocationProviderData {
    val lastLocationFlow: Flow<Location?>

    val lastLocation: Location
}