package com.shevelev.my_footprints_remastered.storages.key_value.storages.combined

import com.shevelev.my_footprints_remastered.storages.key_value.storages.*
import com.syleiman.gingermoney.core.storages.key_value.storages.combined.CombinedStorageUpdateOperations
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import javax.inject.Named

/**
 * Storage based on shared preferences and in-memory structure for cashing
 */
class CombinedStorage
@Inject
constructor(
    @Named(NameConstants.IN_MEMORY)
    private val inMemoryStorage: StorageOperationsInstance,
    @Named(NameConstants.PERSISTENT)
    private val persistentStorage: StorageOperationsInstance
): StorageBase() {

    private val lock = ReentrantReadWriteLock()

    /**
     * Create proxy for read
     */
    override fun createReadOperationsInstance(): StorageReadOperations =
        CombinedStorageReadOperations(
            lock,
            persistentStorage.createReadOperationsInstance(),
            inMemoryStorage.createReadOperationsInstance(),
            inMemoryStorage.createWriteOperationsInstance())

    /**
     * Create proxy for read
     */
    override fun createWriteOperationsInstance(): StorageCommitOperations =
        CombinedStorageUpdateOperations(
            lock,
            persistentStorage.createWriteOperationsInstance(),
            inMemoryStorage.createWriteOperationsInstance())
}