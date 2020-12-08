package com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.sender

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.sync.first_loading_core.FirstLoadingServiceMessageSender
import com.shevelev.my_footprints_remastered.utils.resources.getStringFormatted

/**
 * Update text on notifications
 */
class FirstLoadingServiceMessageSenderNotifications(
    private val context: Context,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManager,
    private val notificationId: Int
) : FirstLoadingServiceMessageSender {

    override fun sendListLoadStarted() = updateText(context.getString(R.string.firstLoadingListLoading))

    override fun sendListLoadCompleted() = updateText(context.getString(R.string.firstLoadingListLoaded))

    override fun sendProgress(current: Int, total: Int) =
        updateText(context.getStringFormatted(R.string.firstLoadingListProgress, current, total))

    override fun sendSuccess() = updateText(context.getString(R.string.firstLoadingListLoaded))

    override fun sendFail() = updateText(context.getString(R.string.firstLoadingListLoaded))

    private fun updateText(text: String) {
        notificationBuilder.setContentText(text)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}