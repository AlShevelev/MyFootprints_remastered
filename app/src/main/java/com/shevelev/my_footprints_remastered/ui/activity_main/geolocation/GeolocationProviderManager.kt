package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

interface GeolocationProviderManager {
    val isLocationTrackingEnabled: Boolean

    suspend fun startTracking()

    fun stopTracking()
}