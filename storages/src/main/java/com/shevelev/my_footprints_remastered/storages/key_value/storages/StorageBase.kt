package com.shevelev.my_footprints_remastered.storages.key_value.storages

/**
 * Base class for a storage
 */
abstract class StorageBase: Storage, StorageOperationsInstance {
    /**
     * Read data from a storage
     */
    override fun <T>read(readFunc: (StorageReadOperations) -> T): T = readFunc(createReadOperationsInstance())

    /**
     * Update data in a storage
     * */
    override fun update(updateAction: (StorageWriteOperations) -> Unit) {
        try{
            val operationsInstance = createWriteOperationsInstance()
            updateAction(operationsInstance)
            operationsInstance.commit()
        } catch(ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    /**
     * Create proxy for read operation
     */
    abstract override fun createReadOperationsInstance(): StorageReadOperations

    /**
     * Create proxy for write operation
     */
    abstract override fun createWriteOperationsInstance(): StorageCommitOperations
}