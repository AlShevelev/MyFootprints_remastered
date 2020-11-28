package com.shevelev.my_footprints_remastered.storages.db.type_converters

import androidx.room.TypeConverter
import com.shevelev.my_footprints_remastered.utils.date_time.*
import org.threeten.bp.ZonedDateTime
import java.nio.ByteBuffer

class ZonedDateTimeTypeConverter {
    @TypeConverter
    fun toDb(sourceData: ZonedDateTime?): ByteArray? = sourceData?.toSplit()?.toBytes()

    @TypeConverter
    fun fromDb(sourceData: ByteArray?): ZonedDateTime? = sourceData?.toZonedDateTimeSplit()?.toZoneDateTime()
}