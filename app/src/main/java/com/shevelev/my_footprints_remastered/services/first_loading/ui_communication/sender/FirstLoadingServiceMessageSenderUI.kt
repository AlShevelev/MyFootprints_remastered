package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.sender

import android.os.Message
import android.os.Messenger
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessage
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessageCode
import com.shevelev.my_footprints_remastered.sync.first_loading_core.FirstLoadingServiceMessageSender

/**
 * Sends messages to UI
 */
class FirstLoadingServiceMessageSenderUI(private val messenger: Messenger) : FirstLoadingServiceMessageSender {
    override fun sendListLoadStarted() = sendSimple(FirstLoadingServiceMessageCode.LIST_LOADING_STARTED)

    override fun sendListLoadCompleted() = sendSimple(FirstLoadingServiceMessageCode.LIST_LOADING_COMPLETED)

    override fun sendProgress(current: Int, total: Int) {
        val message = Message.obtain(
            null,
            FirstLoadingServiceMessageCode.PROGRESS,
            FirstLoadingServiceMessage.Progress(current, total)
        )
        messenger.send(message)
    }

    override fun sendSuccess() = sendSimple(FirstLoadingServiceMessageCode.SUCCESS)

    override fun sendFail() = sendSimple(FirstLoadingServiceMessageCode.FAIL)

    private fun sendSimple(code: Int) = messenger.send(Message.obtain(null, code))
}