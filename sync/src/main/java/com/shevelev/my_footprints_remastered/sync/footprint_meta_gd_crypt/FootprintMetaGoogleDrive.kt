package com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt

/**
 * Encrypted footprint for passing it to GoogleDrive
 */
class FootprintMetaGoogleDrive(
    val comment: List<String>,

    /**
     * Location, Pin color & created time,
     */
    val mainData: String
)