package com.shevelev.my_footprints_remastered.sync.core

interface SyncCore {
    /**
     * @return true in case of success
     */
    fun sync(): Boolean
}