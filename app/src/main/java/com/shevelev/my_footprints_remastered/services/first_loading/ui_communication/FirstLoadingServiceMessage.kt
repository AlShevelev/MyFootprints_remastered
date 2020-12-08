package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication

sealed class FirstLoadingServiceMessage {
    object ListLoadStarted : FirstLoadingServiceMessage()
    object ListLoadCompleted : FirstLoadingServiceMessage()

    class Progress(val current: Int, val total: Int): FirstLoadingServiceMessage()

    object Success : FirstLoadingServiceMessage()
    object Fail : FirstLoadingServiceMessage()
}