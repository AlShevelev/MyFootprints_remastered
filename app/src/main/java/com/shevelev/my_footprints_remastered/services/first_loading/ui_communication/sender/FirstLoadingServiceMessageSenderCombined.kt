package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.sender

import com.shevelev.my_footprints_remastered.sync.first_loading_core.FirstLoadingServiceMessageSender

/**
 * Combined sender
 */
class FirstLoadingServiceMessageSenderCombined
constructor(
    private val senders: List<FirstLoadingServiceMessageSender>
) : FirstLoadingServiceMessageSender {

    override fun sendListLoadStarted() = senders.forEach { it.sendListLoadStarted() }

    override fun sendListLoadCompleted() = senders.forEach { it.sendListLoadCompleted() }

    override fun sendProgress(current: Int, total: Int) = senders.forEach { it.sendProgress(current, total) }

    override fun sendSuccess() = senders.forEach { it.sendSuccess() }

    override fun sendFail() = senders.forEach { it.sendFail() }
}