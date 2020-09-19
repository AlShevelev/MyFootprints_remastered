package com.shevelev.my_footprints_remastered.storages.key_value.storages.in_memory

import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageBase
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageCommitOperations
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageReadOperations
import java.util.*
import javax.inject.Inject

/**
 * Storage based on in-memory dictionary
 */
class InMemoryStorage
@Inject
constructor(): StorageBase() {
    private val storage: MutableMap<String, Any> = TreeMap()

    /**
     * Create proxy for read
     */
    override fun createReadOperationsInstance(): StorageReadOperations = InMemoryStorageOperations(storage)

    /**
     * Create proxy for read
     */
    override fun createWriteOperationsInstance(): StorageCommitOperations = InMemoryStorageOperations(storage)
}