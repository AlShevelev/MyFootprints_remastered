package com.shevelev.my_footprints_remastered.storages.key_value

import com.shevelev.my_footprints_remastered.common_entities.PinColor

interface KeyValueStorageFacade {
    fun savePinColor(pinColor: PinColor)
    fun loadPinColor(): PinColor?
}