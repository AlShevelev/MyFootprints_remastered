package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import com.shevelev.my_footprints_remastered.storages.db.repositories.LastLocationRepository
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@ActivityScope
class GeolocationProviderImpl
@Inject
constructor(
    private val appContext: Context,
    private val dispatchersProvider: DispatchersProvider,
    private val lastLocationRepository: LastLocationRepository
) : GeolocationProviderManager,
    GeolocationProviderFlow,
    GeolocationProviderApp {

    private val minDistance = 15f
    private val maxDistance = 100f
    private val updatesInterval = 10_000L

    private var isTracking = false

    @Volatile
    private var isAppActive = true

    private val locationManager: LocationManager
        get() = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val allProviders: List<String> by lazy { locationManager.allProviders }

    @get:Synchronized @set:Synchronized
    override lateinit var lastLocation: Location
        private set

    private val lastLocationChannel: ConflatedBroadcastChannel<Location> = ConflatedBroadcastChannel()
    override val lastLocationFlow: Flow<Location>
        get() = lastLocationChannel.asFlow()

    override val isLocationTrackingEnabled: Boolean
        get() {
            return locationManager.let {
                it.isProviderEnabled(LocationManager.GPS_PROVIDER) || it.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            }
        }

    private val hasPermissions: Boolean
        get() {
            return appContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

    override suspend fun startTracking() {
        if(!isTracking) {
            if(!::lastLocation.isInitialized) {
                lastLocation = withContext(dispatchersProvider.ioDispatcher) {
                    lastLocationRepository.get()
                } ?: Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = 40.758
                    longitude = 73.9855         // Times Square, NYC, The USA
                    time = Date().time
                }
            }

            isTracking = true

            withContext(dispatchersProvider.calculationsDispatcher) {
                Timber.tag("GEOLOCATION").d("Location tracking started")
                while(isActive) {
                    if(isLocationTrackingEnabled && hasPermissions && isAppActive) {
                        Timber.tag("GEOLOCATION").d("location is received")
                        getBestLocation()?.let { updateLocation(it) }
                    }
                    delay(updatesInterval)
                }
            }
        }
    }

    override fun stopTracking() {
        isTracking = false
        Timber.tag("GEOLOCATION").d("Location tracking completed")
    }

    override fun onAppActive() {
        isAppActive = true
    }

    override fun onAppInactive() {
        isAppActive = false
    }

    private suspend fun updateLocation(newLocation: Location) {
        val oldLocation = lastLocation

        if (newLocation.distanceTo(oldLocation) < maxDistance) {
            if (newLocation.distanceTo(oldLocation) < minDistance) {
                return      // Position didn't change
            }
            if (newLocation.accuracy >= oldLocation.accuracy && newLocation.distanceTo(oldLocation) < newLocation.accuracy) {
                return          // Accuracy got worse and we are still within the accuracy range. Not updating
            }
            if (newLocation.time <= oldLocation.time) {
                return      // Timestamp not never than last
            }
        }

        Timber.tag("GEOLOCATION").d("New location is: $newLocation")

        lastLocation = newLocation

        withContext(dispatchersProvider.ioDispatcher) {
            lastLocationRepository.update(newLocation)
        }

        lastLocationChannel.send(newLocation)
    }

    /**
     * Get the last known location from a specific provider (network/gps)
     */
    private fun getLocationByProvider(provider: String): Location? =
        try {
            provider
                .takeIf { allProviders.contains(it) && locationManager.isProviderEnabled(it) }
                ?.let { locationManager.getLastKnownLocation(it) }
        } catch (ex: SecurityException) {
            Timber.e(ex)
            null
        }

    /**
     * Try to get the 'best' location selected from all providers
     */
    private fun getBestLocation(): Location?
    {
        val gpsLocation = getLocationByProvider(LocationManager.GPS_PROVIDER)
        val networkLocation = getLocationByProvider(LocationManager.NETWORK_PROVIDER)

        return when {
            gpsLocation == null && networkLocation == null -> null
            gpsLocation == null && networkLocation != null -> networkLocation
            gpsLocation != null && networkLocation == null -> gpsLocation
            else -> {
                val oldInterval = System.currentTimeMillis() - updatesInterval

                val gpsIsOld = gpsLocation!!.time < oldInterval
                val networkIsOld = networkLocation!!.time < oldInterval

                when {
                    !gpsIsOld -> gpsLocation
                    !networkIsOld -> networkLocation
                    gpsLocation.time > networkLocation.time -> gpsLocation
                    else -> networkLocation
                }
            }
        }
    }
}