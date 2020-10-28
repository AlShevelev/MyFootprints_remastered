package com.shevelev.my_footprints_remastered.services.di

import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeo
import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ServicesModuleBinds {
    @Binds
    abstract fun provideUpdateGeo(update: UpdateGeoImpl): UpdateGeo
}