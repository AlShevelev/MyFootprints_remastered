package com.shevelev.my_footprints_remastered.utils.date_time

data class ZonedDateTimeSplit(
    val seconds: Long,
    val nanoseconds: Int,
    val timeZoneId: String
)