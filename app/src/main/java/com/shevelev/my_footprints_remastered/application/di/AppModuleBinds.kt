package com.shevelev.my_footprints_remastered.application.di

import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun provideFootprintRepository(repository: FootprintRepositoryImpl): FootprintRepository
}