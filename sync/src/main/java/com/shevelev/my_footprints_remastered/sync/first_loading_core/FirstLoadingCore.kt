package com.shevelev.my_footprints_remastered.sync.first_loading_core

interface FirstLoadingCore {
    fun setMessageSender(sender: FirstLoadingServiceMessageSender)

    fun load()
}