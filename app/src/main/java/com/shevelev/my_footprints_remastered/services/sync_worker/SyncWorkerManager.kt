package com.shevelev.my_footprints_remastered.services.sync_worker

import android.content.Context
import androidx.work.*
import com.shevelev.my_footprints_remastered.BuildConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

object SyncWorkerManager {
    private const val WORK_NAME = "${BuildConfig.APPLICATION_ID}.SYNC_WORKER"

    fun setupWorker(context: Context) {
        try {
//-----------------------------------------------
            // Release
//            val updateConstraints = Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresBatteryNotLow(true)
//                .build()
//
//            val updateWork = PeriodicWorkRequestBuilder<SyncWorker>(8, TimeUnit.HOURS)
//                .setConstraints(updateConstraints)
//                .build()
//
//            WorkManager
//                .getInstance(context)
//                .enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, updateWork)
//-----------------------------------------------
            // Debug
            Timber.tag("SYNC_TEST").d("Work manager is setting up...")
            val updateWork = OneTimeWorkRequestBuilder<SyncWorker>().build()

            WorkManager
                .getInstance(context)
                .enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, updateWork)
            Timber.tag("SYNC_TEST").d("Work manager is setting up...done!")
//-----------------------------------------------
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}