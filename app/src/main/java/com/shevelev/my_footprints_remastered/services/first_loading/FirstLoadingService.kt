package com.shevelev.my_footprints_remastered.services.first_loading

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Messenger
import androidx.core.app.NotificationCompat
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.services.di.ServicesComponent
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.sender.FirstLoadingServiceMessageSenderCombined
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.sender.FirstLoadingServiceMessageSenderNotifications
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.sender.FirstLoadingServiceMessageSenderUI
import com.shevelev.my_footprints_remastered.sync.first_loading_core.FirstLoadingCore
import javax.inject.Inject

/**
 * Service for very first loading of footprints
 */
class FirstLoadingService : IntentService("FirstLoadingService") {
    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY_FIRST_LOADING_SERVICE"

        private const val FOREGROUND_NOTIFICATION_ID = 49086
        private const val FOREGROUND_NOTIFICATION_CHANNEL_ID = "FirstLoadingServiceNotificationChannel"

        private const val ARG_MESSENGER = "ARG_MESSENGER"

        private var _serviceIsRunning = false
        val serviceIsRunning: Boolean
            get() = _serviceIsRunning

        @JvmStatic
        fun start(context: Context, messagesHandler: Handler) {
            if(_serviceIsRunning) {
                return
            }
            _serviceIsRunning = true

            val messenger = Messenger(messagesHandler)
            val intent = Intent(context, FirstLoadingService::class.java).apply {
                putExtra(ARG_MESSENGER, messenger)
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    @Inject
    internal lateinit var core: FirstLoadingCore

    override fun onCreate() {
        super.onCreate()

        setForeground()
        App.injections.get<ServicesComponent>(INJECTION_KEY).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        _serviceIsRunning = false
        App.injections.release<ServicesComponent>(INJECTION_KEY)
    }

    override fun onHandleIntent(intent: Intent?) = processLoading(intent!!.getParcelableExtra(ARG_MESSENGER)!!)

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun processLoading(messenger: Messenger) {
        val messagesSender = FirstLoadingServiceMessageSenderCombined(
            listOf(
                FirstLoadingServiceMessageSenderUI(messenger),
                FirstLoadingServiceMessageSenderNotifications(this, notificationBuilder, notificationManager, FOREGROUND_NOTIFICATION_ID)
            )
        )

        core.setMessageSender(messagesSender)
        core.load()
    }

    private fun setForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = this.getString(R.string.appName)
            val descriptionText = this.getString(R.string.appName)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(FOREGROUND_NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = descriptionText
            notificationManager.createNotificationChannel(channel)
        }

        notificationBuilder = NotificationCompat.Builder(this, FOREGROUND_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.firstLoading))
            .setContentText("")
            .setSmallIcon(R.drawable.ic_launcher)

        startForeground(FOREGROUND_NOTIFICATION_ID, notificationBuilder.build())
    }
}