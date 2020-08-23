package com.shevelev.my_footprints_remastered.storages.db.core

import com.shevelev.my_footprints_remastered.storages.db.dao.FootprintDao
import com.shevelev.my_footprints_remastered.storages.db.dao.LastLocationDao

interface DbCore {
    val footprint: FootprintDao

    val lastLocation: LastLocationDao

    fun <T> runConsistent(action: () -> T): T
}