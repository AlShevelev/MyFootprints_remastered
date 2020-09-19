package com.shevelev.my_footprints_remastered.storages.key_value.storages

interface StorageOperationsInstance {
    /**
     * Create proxy for a read operation
     */
    fun createReadOperationsInstance(): StorageReadOperations

    /**
     * Create proxy for a write operation
     */
    fun createWriteOperationsInstance(): StorageCommitOperations
}