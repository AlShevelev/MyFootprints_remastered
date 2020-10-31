package com.shevelev.my_footprints_remastered.services.update_geo_worker

import android.content.Context
import androidx.work.*
import com.shevelev.my_footprints_remastered.BuildConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

object UpdateGeoWorkerManager {
    private const val WORK_NAME = "${BuildConfig.APPLICATION_ID}.UPDATE_GEO_WORKER"

    fun setupWorker(context: Context) {
        try {
            val updateConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val updateWork = PeriodicWorkRequestBuilder<UpdateGeoWorker>(6, TimeUnit.HOURS)
                .setConstraints(updateConstraints)
                .build()

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, updateWork)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}