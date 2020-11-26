package com.shevelev.my_footprints_remastered.services.di

import com.shevelev.my_footprints_remastered.services.sync_worker.SyncWorker
import com.shevelev.my_footprints_remastered.services.update_geo_service.UpdateGeoService
import com.shevelev.my_footprints_remastered.services.update_geo_worker.UpdateGeoWorker
import com.shevelev.my_footprints_remastered.utils.di_scopes.ServiceScope
import dagger.Subcomponent

@Subcomponent(modules = [ServicesModuleBinds::class, ServicesModule::class])
@ServiceScope
interface ServicesComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ServicesComponent
    }

    fun inject(service: UpdateGeoService)
    fun inject(worker: UpdateGeoWorker)
    fun inject(worker: SyncWorker)
}