package com.shevelev.my_footprints_remastered.application.di

import android.os.Handler
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver.FirstLoadingServiceMessageReceiver
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver.FirstLoadingServiceMessageReceiverImpl
import com.shevelev.my_footprints_remastered.storages.db.repositories.*
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.files.FilesHelperImpl
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacadeImpl
import com.shevelev.my_footprints_remastered.storages.key_value.storages.NameConstants
import com.shevelev.my_footprints_remastered.storages.key_value.storages.Storage
import com.shevelev.my_footprints_remastered.storages.key_value.storages.StorageOperationsInstance
import com.shevelev.my_footprints_remastered.storages.key_value.storages.combined.CombinedStorage
import com.shevelev.my_footprints_remastered.storages.key_value.storages.in_memory.InMemoryStorage
import com.shevelev.my_footprints_remastered.storages.key_value.storages.shared_preferences.SharedPreferencesStorage
import com.shevelev.my_footprints_remastered.sync.db_repositories.SyncRecordRepository
import com.shevelev.my_footprints_remastered.sync.db_repositories.SyncRecordRepositoryImpl
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionHelper
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionHelperImpl
import com.shevelev.my_footprints_remastered.utils.di_scopes.ApplicationScope
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun provideFootprintRepository(repository: FootprintRepositoryImpl): FootprintRepository

    @Binds
    abstract fun provideLastLocationRepository(repository: LastLocationRepositoryImpl): LastLocationRepository

    @Binds
    abstract fun provideSyncRecordRepository(repository: SyncRecordRepositoryImpl): SyncRecordRepository

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

    @Binds
    abstract fun provideFilesHelper(helper: FilesHelperImpl): FilesHelper

    @Binds
    abstract fun provideConnectionHelper(helper: ConnectionHelperImpl): ConnectionHelper

    //region A handler to connect to the FirstLoadingService (must be in ApplicationScope to provide reconnect to the service)
    @Binds
    @ApplicationScope
    abstract fun provideFirstLoadingServiceMessageReceiver(receiver: FirstLoadingServiceMessageReceiverImpl): FirstLoadingServiceMessageReceiver

    @Binds
    @ApplicationScope
    abstract fun provideFirstLoadingServiceMessageHandler(handler: FirstLoadingServiceMessageReceiverImpl): Handler
    //endregion
}