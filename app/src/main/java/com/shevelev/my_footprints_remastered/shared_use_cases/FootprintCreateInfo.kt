package com.shevelev.my_footprints_remastered.shared_use_cases

import android.net.Uri

data class FootprintCreateInfo (
    val lastFootprintId: Long?,
    val lastFootprintImage: Uri?,
    val totalFootprints: Int
)
