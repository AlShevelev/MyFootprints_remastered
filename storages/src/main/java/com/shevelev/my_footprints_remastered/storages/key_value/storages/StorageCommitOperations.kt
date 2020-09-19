package com.shevelev.my_footprints_remastered.storages.key_value.storages

/**
 * Interface for commit operations in a storage
 */
interface StorageCommitOperations: StorageWriteOperations {
    /**
     * Complete editing
     */
    fun commit()
}