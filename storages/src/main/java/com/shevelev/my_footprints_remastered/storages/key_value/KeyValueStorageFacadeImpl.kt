package com.shevelev.my_footprints_remastered.storages.key_value

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
        /**
         * AES-encryption key (for API < 23)
         */
        const val CRYPTO_KEY_AES = "CRYPTO_KEY_AES"
    }

//    override fun setAESCryptoKey(key: ByteArray) =
//        keyValueStorage.update {
//            it.putBytes(Keys.CRYPTO_KEY_AES, key)
//        }
//
//    override fun getAESCryptoKey(): ByteArray? =
//        keyValueStorage.read {
//            it.readBytes(Keys.CRYPTO_KEY_AES)
//        }
}