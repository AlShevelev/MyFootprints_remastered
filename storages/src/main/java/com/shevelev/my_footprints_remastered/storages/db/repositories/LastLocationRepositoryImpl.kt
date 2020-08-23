package com.shevelev.my_footprints_remastered.storages.db.repositories

import android.location.Location
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.storages.db.entities.LastLocationDb
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import java.util.*
import javax.inject.Inject

class LastLocationRepositoryImpl
@Inject
constructor(
    private val db: DbCore
) : LastLocationRepository {
    override fun get(): Location? = db.lastLocation.read()?.mapToLocation()

    override fun update(location: Location) {
        db.runConsistent {
            with(db.lastLocation) {
                val lastLocation = read()
                if(lastLocation == null) {
                    create(LastLocationDb(
                        id = IdUtil.generateLongId(),
                        latitude = location.latitude,
                        longitude = location.longitude,
                        time = Date(),
                        provider = location.provider))
                } else {
                    update(lastLocation.copy(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        time = Date(location.time),
                        provider = location.provider))
                }
            }
        }
    }

    private fun LastLocationDb.mapToLocation() = Location(provider).apply {
        latitude = latitude
        longitude = longitude
        time = time
    }
}