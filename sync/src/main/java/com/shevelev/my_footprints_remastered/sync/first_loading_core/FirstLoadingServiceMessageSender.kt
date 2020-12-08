package com.shevelev.my_footprints_remastered.sync.first_loading_core

interface FirstLoadingServiceMessageSender {
    fun sendListLoadStarted()
    fun sendListLoadCompleted()

    fun sendProgress(current: Int, total: Int)

    fun sendSuccess()
    fun sendFail()
}