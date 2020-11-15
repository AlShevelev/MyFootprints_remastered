package com.shevelev.my_footprints_remastered.shared_use_cases.update_geo

import android.content.Context
import android.location.Geocoder
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionHelper
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionInfo
import dagger.Lazy
import timber.log.Timber
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class UpdateGeoImpl
@Inject
constructor(
    private val appContext: Context,
    private val footprintRepository: Lazy<FootprintRepository>,
    private val keyValueStorage: Lazy<KeyValueStorageFacade>,
    private val connectionHelper: Lazy<ConnectionHelper>
) : UpdateGeo {
    /**
     * Can we load a geographic data?
     * @param singleFootprintRun The value is true if we run a code for a single footprint (not in a bulk mode inside WorkManager)
     * @return The value is true if we can load data
     */
    override fun canLoad(singleFootprintRun: Boolean): Boolean =
        if(singleFootprintRun && !keyValueStorage.get().isCanLoadGeoForSingleFootprint()) {
            false
        } else {
            when(connectionHelper.get().getConnectionInfo()) {
                ConnectionInfo.NO_CONNECTION -> false
                ConnectionInfo.WI_FI -> true
                ConnectionInfo.MOBILE -> !keyValueStorage.get().isUseWiFiToLoadGeoData()
            }
        }

    /**
     * @return The value is true in case of success
     */
    override fun update(footprint: Footprint): Boolean {
        try {
            if (!Geocoder.isPresent()) {
                return false
            } else {
                val geoCoder = Geocoder(appContext, Locale.getDefault())
                val addresses = geoCoder.getFromLocation(footprint.location.latitude, footprint.location.longitude, 1)

                if (addresses.isNullOrEmpty()) {
                    footprintRepository.get().updateGeo(footprint.id, null, null)   // Ocean, desert etc.
                } else {
                    footprintRepository.get().updateGeo(footprint.id, addresses[0].locality, addresses[0].countryName)
                }
            }

            return true
        } catch (ex: Exception) {
            Timber.e(ex)
            return false
        }
    }
}