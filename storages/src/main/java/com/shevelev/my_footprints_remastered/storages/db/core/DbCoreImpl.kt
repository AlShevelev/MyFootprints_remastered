package com.shevelev.my_footprints_remastered.storages.db.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shevelev.my_footprints_remastered.storages.db.dao.FootprintDao
import com.shevelev.my_footprints_remastered.storages.db.entities.FootprintDb
import com.shevelev.my_footprints_remastered.storages.db.type_converters.DateTimeTypeConverter

@Database(
    entities = [
        FootprintDb::class
    ],
    version = 1)
@TypeConverters(
    DateTimeTypeConverter::class)
abstract class DbCoreImpl: RoomDatabase(), DbCore {
    abstract override val footprint: FootprintDao

    /**
     * Run some code in transaction
     */
    override fun <T>runConsistent(action: () -> T): T =
        runInTransaction<T> {
            action()
        }
}