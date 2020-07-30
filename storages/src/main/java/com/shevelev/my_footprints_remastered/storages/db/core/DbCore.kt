package com.shevelev.my_footprints_remastered.storages.db.core

import com.shevelev.my_footprints_remastered.storages.db.dao.FootprintDao

interface DbCore {
    val footprint: FootprintDao

    fun <T> runConsistent(action: () -> T): T
}