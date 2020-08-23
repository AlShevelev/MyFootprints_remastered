package com.shevelev.my_footprints_remastered.storages.db.type_converters

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun toDb(sourceData: Date?): Long? = sourceData?.time

    @TypeConverter
    fun fromDb(sourceData: Long?): Date? = sourceData?.let { Date(it) }
}