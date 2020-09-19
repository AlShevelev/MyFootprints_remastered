package com.shevelev.my_footprints_remastered.storages.key_value.storages.shared_preferences

import android.content.Context
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageBase
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageCommitOperations
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageReadOperations
import javax.inject.Inject

/**
 * Storage based on shared preferences
 */
class SharedPreferencesStorage
@Inject
constructor(
    private val context: Context
) : StorageBase() {

    private val storageName
        get() = "${context.packageName}.App"

    /**
     * Create proxy for read operation
     */
    override fun createReadOperationsInstance(): StorageReadOperations =
        SharedPreferencesStorageReadOperations(context, storageName)

    /**
     * Create proxy for write operation
     */
    override fun createWriteOperationsInstance(): StorageCommitOperations =
        SharedPreferencesStorageUpdateOperations(context, storageName)
}