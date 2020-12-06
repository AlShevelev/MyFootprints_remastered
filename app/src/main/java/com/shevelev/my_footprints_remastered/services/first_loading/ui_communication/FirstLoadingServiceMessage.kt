package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication

sealed class FirstLoadingServiceMessage {
    class Progress(val current: Int, val total: Int): FirstLoadingServiceMessage()

    object Complete : FirstLoadingServiceMessage()
}