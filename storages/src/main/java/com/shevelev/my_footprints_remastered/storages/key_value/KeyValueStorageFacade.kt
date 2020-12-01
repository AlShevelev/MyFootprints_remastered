package com.shevelev.my_footprints_remastered.storages.key_value

import com.shevelev.my_footprints_remastered.common_entities.PinColor

interface KeyValueStorageFacade {
    fun savePinColor(pinColor: PinColor)
    fun loadPinColor(): PinColor?

    fun isUseWiFiToLoadGeoData(): Boolean
    fun setUseWiFiToLoadGeoData(value: Boolean)

    fun isCanLoadGeoForSingleFootprint(): Boolean
    fun setCanLoadGeoForSingleFootprint(value: Boolean)

    fun isUseWiFiToBackup(): Boolean
    fun setUseWiFiToBackup(value: Boolean)

    fun isStartLoadingCompleted(): Boolean
    fun setStartLoadingCompleted(value: Boolean)
}