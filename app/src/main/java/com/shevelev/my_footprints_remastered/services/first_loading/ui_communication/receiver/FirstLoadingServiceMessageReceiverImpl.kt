package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver

import android.os.Handler
import android.os.Message
import com.shevelev.my_footprints_remastered.services.first_loading.FirstLoadingService
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessage
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessageCode

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
            FirstLoadingServiceMessageCode.PROGRESS ->
                onMessageListener?.invoke(msg.obj as FirstLoadingServiceMessage.Progress)

            FirstLoadingServiceMessageCode.SUCCESS ->
                onMessageListener?.invoke(FirstLoadingServiceMessage.Success)

            FirstLoadingServiceMessageCode.FAIL ->
                onMessageListener?.invoke(FirstLoadingServiceMessage.Fail)

            FirstLoadingServiceMessageCode.LIST_LOADING_STARTED ->
                onMessageListener?.invoke(FirstLoadingServiceMessage.ListLoadStarted)

            FirstLoadingServiceMessageCode.LIST_LOADING_COMPLETED ->
                onMessageListener?.invoke(FirstLoadingServiceMessage.ListLoadCompleted)
        }
    }
}