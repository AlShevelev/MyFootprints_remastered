package com.shevelev.my_footprints_remastered.services.di

import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeo
import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeoImpl
import com.shevelev.my_footprints_remastered.sync.db_repositories.FirstLoadRecordRepository
import com.shevelev.my_footprints_remastered.sync.db_repositories.FirstLoadRecordRepositoryImpl
import com.shevelev.my_footprints_remastered.sync.first_loading_core.FirstLoadingCore
import com.shevelev.my_footprints_remastered.sync.first_loading_core.FirstLoadingCoreImpl
import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDriveCrypt
import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDriveCryptImpl
import com.shevelev.my_footprints_remastered.sync.sync_core.SyncCore
import com.shevelev.my_footprints_remastered.sync.sync_core.SyncCoreImpl
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentialsImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ServicesModuleBinds {
    @Binds
    abstract fun provideUpdateGeo(update: UpdateGeoImpl): UpdateGeo

    @Binds
    abstract fun provideSyncCore(core: SyncCoreImpl): SyncCore

    @Binds
    abstract fun provideGoogleDriveCredentials(credentials: GoogleDriveCredentialsImpl): GoogleDriveCredentials

    @Binds
    abstract fun provideFootprintMetaGoogleDriveCrypt(crypt: FootprintMetaGoogleDriveCryptImpl): FootprintMetaGoogleDriveCrypt

    @Binds
    abstract fun provideFirstLoadRecordRepository(repository: FirstLoadRecordRepositoryImpl): FirstLoadRecordRepository

    @Binds
    abstract fun provideFirstLoadingCore(core: FirstLoadingCoreImpl): FirstLoadingCore
}