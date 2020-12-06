package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication

import android.os.Handler
import android.os.Message
import com.shevelev.my_footprints_remastered.services.first_loading.FirstLoadingService

/**
 * Receive messages from [FirstLoadingService]
 */
class FirstLoadingServiceMessageReceiverImpl : FirstLoadingServiceMessageReceiver, Handler() {

    private var onMessageListener: ((FirstLoadingServiceMessage) -> Unit)? = null

    override fun setOnMessageListener(listener: ((FirstLoadingServiceMessage) -> Unit)?) {
        onMessageListener = listener
    }

    override fun handleMessage(msg: Message) {
        when(msg.what) {
            FirstLoadingServiceMessageCode.PROGRESS -> onMessageListener?.invoke(msg.obj as FirstLoadingServiceMessage.Progress)
            FirstLoadingServiceMessageCode.COMPLETE -> onMessageListener?.invoke(FirstLoadingServiceMessage.Complete)
        }
    }
}