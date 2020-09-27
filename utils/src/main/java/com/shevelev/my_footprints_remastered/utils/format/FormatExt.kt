package com.shevelev.my_footprints_remastered.utils.format

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

fun ZonedDateTime.toShortDisplayString(): String =
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).format(this)