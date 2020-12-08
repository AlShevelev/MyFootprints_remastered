package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver

import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessage

interface FirstLoadingServiceMessageReceiver {
    fun setOnMessageListener(listener: ((FirstLoadingServiceMessage) -> Unit)?)
}