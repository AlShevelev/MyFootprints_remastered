package com.shevelev.my_footprints_remastered.utils.device_info

import android.util.Size

interface DeviceInfoProvider {
    /**
     * Returns ISO 3166-1 code of country of null if the code can't be detected
     */
    fun getCountryCode(): String?

    fun getSizeInPix(): Size

    /** */
    fun getSizeInDp(): Size

    /** */
    fun getSizeCategory(): DisplaySizeCategory

    /** */
    fun getDensityCategory(): DisplayDensityCategory
}