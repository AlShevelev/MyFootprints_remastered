package com.shevelev.my_footprints_remastered.storages.key_value.storages

/**
 * External interface of a storage
 */
interface Storage {
    /**
     * Read data from a storage
     */
    fun <T>read(readFunc: (StorageReadOperations) -> T): T

    /**
     * Update data in a storage
     */
    fun update(updateAction: (StorageWriteOperations) -> Unit)
}