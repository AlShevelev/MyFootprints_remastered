package com.shevelev.my_footprints_remastered.services.di

import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeo
import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeoImpl
import com.shevelev.my_footprints_remastered.sync.core.SyncCore
import com.shevelev.my_footprints_remastered.sync.core.SyncCoreImpl
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
}