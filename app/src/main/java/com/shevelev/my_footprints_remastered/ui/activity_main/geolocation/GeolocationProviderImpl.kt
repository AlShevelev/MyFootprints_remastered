package com.shevelev.my_footprints_remastered.ui.activity_main.geolocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.shevelev.my_footprints_remastered.storages.db.repositories.LastLocationRepository
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

@ActivityScope
class GeolocationProviderImpl
@Inject
constructor(
    private val appContext: Context,
    private val dispatchersProvider: DispatchersProvider,
    private val lastLocationRepository: LastLocationRepository
) : GeolocationProviderManager,
    GeolocationProviderData,
    GeolocationProviderApp,
    GeolocationProviderListener {

    private val minDistance = 15f       // [m]
    private val maxDistance = 100f      // [m]
    private val updatesInterval = 10_000L       // [ms]

    private var isTracking = false

    @Volatile
    private var isAppActive = true

    private val locationManager: LocationManager
        get() = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val allProviders: List<String> by lazy { locationManager.allProviders }

    private val listeners = mutableMapOf<String, LocationListener>()

    @get:Synchronized @set:Synchronized
    override lateinit var lastLocation: Location
        private set

    private val lastLocationMutableFlow: MutableStateFlow<Location?> = MutableStateFlow(null)
    override val lastLocationFlow: Flow<Location?>
        get() = lastLocationMutableFlow.asStateFlow()

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
                    latitude = 40.758896
                    longitude = -73.985130         // Times Square, NYC, The USA
                    time = Date().time
                }
            }

            Timber.tag("LOCATION").d("First location: ${lastLocation.latitude}:${lastLocation.longitude}")
            lastLocationMutableFlow.value = lastLocation

            isTracking = true

            if(hasPermissions && isAppActive) {
                allProviders.forEach { registerListener(it) }
            }
        }
    }

    override fun stopTracking() {
        isTracking = false

        allProviders.forEach { provider ->
            listeners.remove(provider)?.let { locationManager.removeUpdates(it) }
        }
        Timber.tag("GEOLOCATION").d("Location tracking completed")
    }

    override fun onAppActive() {
        isAppActive = true
    }

    override fun onAppInactive() {
        isAppActive = false
    }

    override fun onLocationUpdated(newLocation: Location, provider: String) {
        val oldLocation = lastLocation

        if (newLocation.distanceTo(oldLocation) < maxDistance && provider == LocationManager.NETWORK_PROVIDER) {
            if (newLocation.distanceTo(oldLocation) < minDistance) {
                return      // Position didn't change
            }
            if (newLocation.accuracy >= oldLocation.accuracy && newLocation.distanceTo(oldLocation) < newLocation.accuracy) {
                return          // Accuracy got worse and we are still within the accuracy range. Not updating
            }
        }

        Timber.tag("GEOLOCATION").d("New location is: $newLocation")

        lastLocation = newLocation

        Executors.newSingleThreadExecutor().execute {
            lastLocationRepository.update(newLocation)
        }

        lastLocationMutableFlow.value = newLocation
    }

    private fun registerListener(provider: String) {
        val listener = LocationListenerImpl(provider, this)

        try {
            locationManager.requestLocationUpdates(provider, updatesInterval, minDistance, listener)
        } catch (ex: SecurityException) {
            Timber.e(ex)
        }

        listeners[provider] = listener
    }
}