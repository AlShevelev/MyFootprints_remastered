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
        const val USE_WIFI_TO_LOAD_GEO_DATA = "USE_WIFI_TO_LOAD_GEO_DATA"
        const val CAN_LOAD_GEO_FOR_SINGLE_FOOTPRINT = "CAN_LOAD_GEO_FOR_SINGLE_FOOTPRINT"
        const val USE_WIFI_TO_BACKUP = "USE_WIFI_TO_BACKUP"
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

    override fun isUseWiFiToLoadGeoData(): Boolean =
        keyValueStorage.read {
            it.readBoolean(Keys.USE_WIFI_TO_LOAD_GEO_DATA) ?: true
        }

    override fun setUseWiFiToLoadGeoData(value: Boolean) =
        keyValueStorage.update {
            it.putBoolean(Keys.USE_WIFI_TO_LOAD_GEO_DATA, value)
        }

    override fun isCanLoadGeoForSingleFootprint(): Boolean =
        keyValueStorage.read {
            it.readBoolean(Keys.CAN_LOAD_GEO_FOR_SINGLE_FOOTPRINT) ?: true
        }


    override fun setCanLoadGeoForSingleFootprint(value: Boolean) =
        keyValueStorage.update {
            it.putBoolean(Keys.CAN_LOAD_GEO_FOR_SINGLE_FOOTPRINT, value)
        }

    override fun isUseWiFiToBackup(): Boolean =
        keyValueStorage.read {
            it.readBoolean(Keys.USE_WIFI_TO_BACKUP) ?: true
        }

    override fun setUseWiFiToBackup(value: Boolean) =
        keyValueStorage.update {
            it.putBoolean(Keys.USE_WIFI_TO_BACKUP, value)
        }
}