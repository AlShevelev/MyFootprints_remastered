package com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint

data class FootprintDeleteInfo(
    val lastFootprintId: Long?,
    val lastFootprintImageFileName: String?,
    val totalFootprints: Int
)