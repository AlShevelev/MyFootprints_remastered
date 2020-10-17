package com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.push

/**
 * Base class for passing data across fragments via push model (request - response)
 */
abstract class FragmentsDataPushBase {
    private val dataStorage: MutableMap<Int, Any> by lazy { mutableMapOf<Int, Any>() }

    protected fun put(key: Int, value: Any) {
        dataStorage[key] = value
    }

    /**
     * Gets data item from the storage
     */
    protected fun get(key: Int): Any? = dataStorage[key]

    /**
     * Gets data item from the storage and remove the item
     */
    protected fun remove(key: Int): Any? = dataStorage.remove(key)

    protected fun contains(key: Int): Boolean = dataStorage.contains(key)
}