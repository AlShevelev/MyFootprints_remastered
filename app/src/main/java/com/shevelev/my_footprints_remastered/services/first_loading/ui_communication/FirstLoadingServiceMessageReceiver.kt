package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication

interface FirstLoadingServiceMessageReceiver {
    fun setOnMessageListener(listener: ((FirstLoadingServiceMessage) -> Unit)?)
}