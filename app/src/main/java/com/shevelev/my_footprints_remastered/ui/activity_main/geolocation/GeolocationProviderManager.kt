package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

interface GeolocationProviderManager : GeolocationProviderData {
    val isLocationTrackingEnabled: Boolean

    suspend fun startTracking()

    fun stopTracking()
}