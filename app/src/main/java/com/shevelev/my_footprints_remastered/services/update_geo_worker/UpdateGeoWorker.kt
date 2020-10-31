package com.shevelev.my_footprints_remastered.services.update_geo_worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.services.di.ServicesComponent
import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeo
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import dagger.Lazy
import timber.log.Timber
import javax.inject.Inject

class UpdateGeoWorker(
    context : Context,
    params : WorkerParameters
) : Worker(context, params) {

    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY_GEO_WORKER"
    }

    @Inject
    internal lateinit var updateGeoUseCase: UpdateGeo

    @Inject
    internal lateinit var footprintRepository: Lazy<FootprintRepository>

    init {
        App.injections.get<ServicesComponent>(INJECTION_KEY).inject(this)
    }

    override fun doWork(): Result {
        try {
            if(updateGeoUseCase.canLoad(false)) {
                val footprintToProcess = footprintRepository.get().getAllWithoutGeo()

                footprintToProcess.forEach {
                    if(!updateGeoUseCase.update(it)) {
                        return Result.retry()
                    }
                }
            }

            return Result.success()
        } catch (ex: Exception) {
            Timber.e(ex)
            return Result.retry()
        } finally {
            App.injections.release<ServicesComponent>(INJECTION_KEY)
        }
    }

    override fun onStopped() {
        super.onStopped()
        App.injections.release<ServicesComponent>(INJECTION_KEY)
    }
}