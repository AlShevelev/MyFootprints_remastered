package com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt

import com.shevelev.my_footprints_remastered.common_entities.Footprint

interface FootprintMetaGoogleDriveCrypt {
    fun encrypt(footprint: Footprint): FootprintMetaGoogleDrive

    fun decrypt(gdData: FootprintMetaGoogleDrive): Footprint
}