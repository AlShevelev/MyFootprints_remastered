package com.shevelev.my_footprints_remastered.storages.key_value.storages.combined

import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageCommitOperations
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageReadOperations
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class CombinedStorageReadOperations(
    private val lock: ReentrantReadWriteLock,
    private val persistentStorage: StorageReadOperations,
    private val cacheStorageRead: StorageReadOperations,
    private val cacheStorageUpdate: StorageCommitOperations
): StorageReadOperations {

    /**
     * Check is item exists
     */
    override fun contains(key: String): Boolean {
        lock.read {
            return if(cacheStorageRead.contains(key)) {
                true
            } else {
                persistentStorage.contains(key)
            }
        }
    }

    /**
     * Read boolean value
     */
    override fun readBoolean(key: String): Boolean? {
        return read(
            { cacheStorageRead.readBoolean(key) },
            { persistentStorage.readBoolean(key) },
            { cacheStorageUpdate.putBoolean(key, it) }
        )
    }

    /**
     * Read string value
     */
    override fun readString(key: String): String? {
        return read(
            { cacheStorageRead.readString(key) },
            { persistentStorage.readString(key) },
            { cacheStorageUpdate.putString(key, it) }
        )
    }

    /**
     * Read float value
     */
    override fun readFloat(key: String): Float? {
        return read(
            { cacheStorageRead.readFloat(key) },
            { persistentStorage.readFloat(key) },
            { cacheStorageUpdate.putFloat(key, it) }
        )
    }

    /**
     * Read int value
     */
    override fun readInt(key: String): Int? {
        return read(
            { cacheStorageRead.readInt(key) },
            { persistentStorage.readInt(key) },
            { cacheStorageUpdate.putInt(key, it) }
        )
    }

    /**
     * Read long value
     */
    override fun readLong(key: String): Long? {
        return read(
            { cacheStorageRead.readLong(key) },
            { persistentStorage.readLong(key) },
            { cacheStorageUpdate.putLong(key, it) }
        )
    }

    /**
     * Read long value
     */
    override fun readBytes(key: String): ByteArray? {
        return read(
            { cacheStorageRead.readBytes(key) },
            { persistentStorage.readBytes(key) },
            { cacheStorageUpdate.putBytes(key, it) }
        )
    }

    private fun <T>read(
        readFromCacheAction: () -> T?,
        readFromPersistentStorageAction: () -> T?,
        writeToCacheAction: (T) -> Unit): T? {

        var value: T?
        var needToUpdateCache: Boolean

        lock.read {
            value = readFromCacheAction()
            if(value != null) {
                return value
            } else {
                value = readFromPersistentStorageAction()
                if(value == null) {
                    return null
                } else {
                    needToUpdateCache = true
                }
            }
        }

        if(needToUpdateCache) {
            lock.write {
                writeToCacheAction(value!!)
            }
        }

        return value
    }
}