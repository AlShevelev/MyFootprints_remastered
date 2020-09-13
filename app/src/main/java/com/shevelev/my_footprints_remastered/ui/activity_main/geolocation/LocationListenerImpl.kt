package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import timber.log.Timber

class LocationListenerImpl(
    private val provider: String,
    private val listener: GeolocationProviderListener
) : LocationListener {
    override fun onLocationChanged(location: Location?) {
        Timber.tag("LOCATION").d("Provider [$provider] sends a location ${location?.latitude};${location?.longitude}")
        location?.let { listener.onLocationUpdated(it, provider) }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // do nothing
    }

    override fun onProviderEnabled(provider: String?) {
        Timber.tag("LOCATION").d("Provider [$provider] is enabled")
        // do nothing
    }

    override fun onProviderDisabled(provider: String?) {
        Timber.tag("LOCATION").d("Provider [$provider] is disabled")
        // do nothing
    }
}