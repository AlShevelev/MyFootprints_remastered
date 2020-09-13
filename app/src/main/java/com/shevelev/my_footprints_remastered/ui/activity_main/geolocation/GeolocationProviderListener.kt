package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

import android.location.Location

interface GeolocationProviderListener {
    fun onLocationUpdated(newLocation: Location, provider: String)
}