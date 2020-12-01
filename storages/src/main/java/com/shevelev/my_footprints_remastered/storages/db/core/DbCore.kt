package com.shevelev.my_footprints_remastered.storages.db.core

import com.shevelev.my_footprints_remastered.storages.db.dao.FirstLoadRecordDao
import com.shevelev.my_footprints_remastered.storages.db.dao.FootprintDao
import com.shevelev.my_footprints_remastered.storages.db.dao.LastLocationDao
import com.shevelev.my_footprints_remastered.storages.db.dao.SyncRecordDao

interface DbCore {
    val footprint: FootprintDao

    val lastLocation: LastLocationDao

    val syncRecord: SyncRecordDao

    val firstLoadRecord: FirstLoadRecordDao

    fun <T> runConsistent(action: () -> T): T
}