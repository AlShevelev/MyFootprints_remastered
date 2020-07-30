package com.shevelev.my_footprints_remastered.common_entities

import org.threeten.bp.ZonedDateTime

data class Footprint (
    val id: Long,
    val fileName: String,
    val created: ZonedDateTime
)