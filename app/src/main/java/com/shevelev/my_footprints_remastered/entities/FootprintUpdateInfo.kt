package com.shevelev.my_footprints_remastered.entities

import android.net.Uri

data class FootprintUpdateInfo (
    val lastFootprintImage: Uri,
    val totalFootprints: Int
)