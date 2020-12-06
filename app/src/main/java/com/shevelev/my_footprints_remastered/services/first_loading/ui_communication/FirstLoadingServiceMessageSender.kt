package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication

import android.os.Message
import android.os.Messenger
import com.shevelev.my_footprints_remastered.services.first_loading.FirstLoadingService

/**
 * Send messages from [FirstLoadingService]
 */
class FirstLoadingServiceMessageSender(private val messenger: Messenger) {
    fun sentProgress(current: Int, total: Int) {
        val message = Message.obtain(
            null,
            FirstLoadingServiceMessageCode.PROGRESS,
            FirstLoadingServiceMessage.Progress(current, total))
        messenger.send(message)
    }

    fun sendCompleted() {
        val message = Message.obtain(null, FirstLoadingServiceMessageCode.COMPLETE)
        messenger.send(message)
    }
}