package com.shevelev.my_footprints_remastered.utils.date_time

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.nio.ByteBuffer

fun ZonedDateTime.toSplit(): ZonedDateTimeSplit {
    val zone = this.zone.id

    val nowInstant = this.toInstant()

    val seconds = nowInstant.epochSecond
    val nanoseconds = nowInstant.nano

    return ZonedDateTimeSplit(seconds, nanoseconds, zone)
}

fun ZonedDateTimeSplit.toZoneDateTime(): ZonedDateTime {
    val restoredInstant = Instant.ofEpochSecond(seconds, nanoseconds.toLong())
    return ZonedDateTime.ofInstant(restoredInstant, ZoneId.of(this.timeZoneId))
}

fun ZonedDateTimeSplit.toBytes(): ByteArray {
    val timeZoneAsBytes = this.timeZoneId.toByteArray(Charsets.UTF_8)

    return ByteBuffer.allocate(Long.SIZE_BYTES + Int.SIZE_BYTES + timeZoneAsBytes.size)
        .putLong(this.seconds)
        .putInt(this.nanoseconds)
        .put(timeZoneAsBytes)
        .array()
}

fun ByteArray.toZonedDateTimeSplit(): ZonedDateTimeSplit {
    val buffer = ByteBuffer.wrap(this)

    val seconds = buffer.getLong(0)
    val nanoSeconds = buffer.getInt(Long.SIZE_BYTES)

    val timeZoneOffset = Long.SIZE_BYTES + Int.SIZE_BYTES

    val timeZoneAsBytes = ByteArray(this.size - timeZoneOffset)

    for(i in timeZoneOffset until this.size) {
        timeZoneAsBytes[i-timeZoneOffset] = buffer.get(i)
    }

    return ZonedDateTimeSplit(seconds, nanoSeconds, timeZoneAsBytes.toString(Charsets.UTF_8))
}