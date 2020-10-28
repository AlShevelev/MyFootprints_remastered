package com.shevelev.my_footprints_remastered.services.update_geo

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.services.di.ServicesComponent
import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeo
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

/**
 * A service for update geographic data
 */
class UpdateGeoService : IntentService("UpdateGeoService") {
    companion object {
        private const val ACTION_UPDATE = "ACTION_UPDATE"
        private const val ARG_FOOTPRINT = "ARG_FOOTPRINT"
        private const val INJECTION_KEY = "INJECTION_KEY"

        @JvmStatic
        fun start(context: Context, footprint: Footprint) {
            val intent = Intent(context, UpdateGeoService::class.java).apply {
                action = ACTION_UPDATE
                putExtra(ARG_FOOTPRINT, footprint)
            }
            context.startService(intent)
        }
    }

    @Inject
    internal lateinit var updateGeoUseCase: UpdateGeo

    override fun onCreate() {
        super.onCreate()
        App.injections.get<ServicesComponent>(INJECTION_KEY).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.injections.release<ServicesComponent>(INJECTION_KEY)
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_UPDATE -> processUpdate(intent.getParcelableExtra(ARG_FOOTPRINT) as Footprint)
        }
    }

    private fun processUpdate(footprint: Footprint) {
        try {
            if(updateGeoUseCase.canLoad(true)) {
                updateGeoUseCase.update(footprint)
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}