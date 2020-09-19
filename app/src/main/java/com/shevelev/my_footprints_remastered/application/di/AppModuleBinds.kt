package com.shevelev.my_footprints_remastered.application.di

import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepositoryImpl
import com.shevelev.my_footprints_remastered.storages.db.repositories.LastLocationRepository
import com.shevelev.my_footprints_remastered.storages.db.repositories.LastLocationRepositoryImpl
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacadeImpl
import com.shevelev.my_footprints_remastered.storages.key_value.storages.NameConstants
import com.shevelev.my_footprints_remastered.storages.key_value.storages.Storage
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageOperationsInstance
import com.shevelev.my_footprints_remastered.storages.key_value.storages.combined.CombinedStorage
import com.shevelev.my_footprints_remastered.storages.key_value.storages.in_memory.InMemoryStorage
import com.shevelev.my_footprints_remastered.storages.key_value.storages.shared_preferences.SharedPreferencesStorage
import com.shevelev.my_footprints_remastered.utils.di_scopes.ApplicationScope
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun provideFootprintRepository(repository: FootprintRepositoryImpl): FootprintRepository

    @Binds
    abstract fun provideLastLocationRepository(repository: LastLocationRepositoryImpl): LastLocationRepository

    //region Key-value storage
    @Binds
    @ApplicationScope
    @Named(NameConstants.IN_MEMORY)
    abstract fun provideInMemoryStorage(storage: InMemoryStorage): StorageOperationsInstance

    @Binds
    @Named(NameConstants.PERSISTENT)
    abstract fun provideSharedPreferencesStorage(storage: SharedPreferencesStorage): StorageOperationsInstance

    @Binds
    @Named(NameConstants.COMBINED)
    abstract fun provideCombinedStorage(storage: CombinedStorage): Storage

    @Binds
    abstract fun provideKeyValueStorageFacade(facade: KeyValueStorageFacadeImpl): KeyValueStorageFacade
    //endregion
}