package com.shevelev.my_footprints_remastered.storages.db.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shevelev.my_footprints_remastered.storages.db.dao.FootprintDao
import com.shevelev.my_footprints_remastered.storages.db.dao.LastLocationDao
import com.shevelev.my_footprints_remastered.storages.db.dao.SyncRecordDao
import com.shevelev.my_footprints_remastered.storages.db.entities.FootprintDb
import com.shevelev.my_footprints_remastered.storages.db.entities.LastLocationDb
import com.shevelev.my_footprints_remastered.storages.db.entities.SyncRecordDb
import com.shevelev.my_footprints_remastered.storages.db.type_converters.DateTypeConverter
import com.shevelev.my_footprints_remastered.storages.db.type_converters.ZonedDateTimeTypeConverter

@Database(
    entities = [
        FootprintDb::class,
        LastLocationDb::class,
        SyncRecordDb::class
    ],
    version = 1)
@TypeConverters(
    ZonedDateTimeTypeConverter::class,
    DateTypeConverter::class)
abstract class DbCoreImpl: RoomDatabase(), DbCore {
    abstract override val footprint: FootprintDao

    abstract override val lastLocation: LastLocationDao

    abstract override val syncRecord: SyncRecordDao
    
    /**
     * Run some code in transaction
     */
    override fun <T>runConsistent(action: () -> T): T =
        runInTransaction<T> {
            action()
        }
}