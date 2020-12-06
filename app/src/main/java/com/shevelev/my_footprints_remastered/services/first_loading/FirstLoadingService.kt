package com.shevelev.my_footprints_remastered.services.first_loading

import android.app.IntentService
import android.app.Notification
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
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.services.di.ServicesComponent
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessage
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessageSender
import com.shevelev.my_footprints_remastered.services.update_geo_service.UpdateGeoService
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * Service for very first loading of footprints
 */
class FirstLoadingService : IntentService("FirstLoadingService") {
    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY_FIRST_LOADING_SERVICE"

        private const val FOREGROUND_NOTIFICATION_ID = 49086
        private const val FOREGROUND_NOTIFICATION_CHANNEL_ID = "FirstLoadingServiceNotificationChannel"

        private const val ARG_MESSENGER = "ARG_MESSENGER"

        private var serviceIsRunning = false

        @JvmStatic
        fun start(context: Context, messagesHandler: Handler) {
            if(serviceIsRunning) {
                return
            }
            serviceIsRunning = true

            Timber.tag("FIRST_LOADING").d("[${Thread.currentThread().name}]FirstLoadingService.start")
            val messenger = Messenger(messagesHandler)
            val intent = Intent(context, FirstLoadingService::class.java).apply {
                putExtra(ARG_MESSENGER, messenger)
            }
            context.startForegroundService(intent)
        }
    }

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate() {
        super.onCreate()

        setForeground()
//        App.injections.get<ServicesComponent>(UpdateGeoService.INJECTION_KEY).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        serviceIsRunning = false
//        App.injections.release<ServicesComponent>(UpdateGeoService.INJECTION_KEY)
    }

    override fun onHandleIntent(intent: Intent?) = processLoading(intent!!.getParcelableExtra(ARG_MESSENGER)!!)

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun processLoading(messenger: Messenger) {
        val messagesSender = FirstLoadingServiceMessageSender(messenger)

        for(i in 0..9) {
            Timber.tag("FIRST_LOADING").d("[${Thread.currentThread().name}]FirstLoadingService.processLoading... $i")
            updateNotificationProgress(i, 9)
            messagesSender.sentProgress(i, 9)
            Thread.sleep(1000L)
        }
        messagesSender.sendCompleted()
    }

    private fun setForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Test channel"
            val descriptionText = "Test channel description"
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(FOREGROUND_NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            notificationManager.createNotificationChannel(mChannel)
        }

        notificationBuilder = NotificationCompat.Builder(this, FOREGROUND_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getText(R.string.appName))
            .setContentText("Some text")
            .setSmallIcon(R.mipmap.ic_launcher)

        startForeground(FOREGROUND_NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun updateNotificationProgress(current: Int, total: Int) {
        notificationBuilder.setContentText("$current from $total")
        notificationManager.notify(FOREGROUND_NOTIFICATION_ID, notificationBuilder.build())
    }
}