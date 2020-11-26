package com.shevelev.my_footprints_remastered.services.sync_worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.services.di.ServicesComponent
import com.shevelev.my_footprints_remastered.sync.core.SyncCore
import javax.inject.Inject

class SyncWorker(
    context : Context,
    params : WorkerParameters
) : Worker(context, params) {
    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY_SYNC_WORKER"
    }

    init {
        App.injections.get<ServicesComponent>(INJECTION_KEY).inject(this)
    }

    @Inject
    internal lateinit var core: SyncCore

    override fun doWork(): Result = if(core.sync()) Result.success() else Result.retry()

    override fun onStopped() {
        super.onStopped()
        App.injections.release<ServicesComponent>(INJECTION_KEY)
    }
}