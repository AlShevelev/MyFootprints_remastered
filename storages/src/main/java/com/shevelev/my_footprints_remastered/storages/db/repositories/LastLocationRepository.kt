package com.shevelev.my_footprints_remastered.storages.db.repositories

import android.location.Location

interface LastLocationRepository {
    fun get(): Location?

    fun update(location: Location)
}