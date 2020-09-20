package com.shevelev.my_footprints_remastered.storages.key_value

import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.storages.key_value.storages.NameConstants
import com.shevelev.my_footprints_remastered.storages.key_value.storages.Storage
import javax.inject.Inject
import javax.inject.Named

/**
 * Helper class for access to App-level private shared preferences
 */
class KeyValueStorageFacadeImpl
@Inject
constructor(
    @Named(NameConstants.COMBINED)
    private val keyValueStorage: Storage
) : KeyValueStorageFacade {

    private object Keys {
        const val PIN_COLOR_TEXT = "PIN_COLOR_TEXT"
        const val PIN_COLOR_BACKGROUND = "PIN_COLOR_BACKGROUND"
    }

    override fun savePinColor(pinColor: PinColor) {
        keyValueStorage.update {
            it.putInt(Keys.PIN_COLOR_TEXT, pinColor.textColor)
            it.putInt(Keys.PIN_COLOR_BACKGROUND, pinColor.backgroundColor)
        }
    }

    override fun loadPinColor(): PinColor? =
        keyValueStorage.read {
            val textColor = it.readInt(Keys.PIN_COLOR_TEXT)
            val backgroundColor = it.readInt(Keys.PIN_COLOR_BACKGROUND)

            if(textColor != null && backgroundColor != null) {
                PinColor(textColor, backgroundColor)
            } else {
                null
            }
        }
}