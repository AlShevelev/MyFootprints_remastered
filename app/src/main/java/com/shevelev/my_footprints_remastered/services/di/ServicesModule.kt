package com.shevelev.my_footprints_remastered.services.di

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.sync.gd_low_level.GoogleDriveOperations
import com.shevelev.my_footprints_remastered.sync.gd_low_level.GoogleDriveOperationsImpl
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import dagger.Module
import dagger.Provides

@Module
class ServicesModule {
    @Provides
    fun provideGoogleDriveOperations(
        appContext: Context,
        gdCredentials: GoogleDriveCredentials): GoogleDriveOperations =
        GoogleDriveOperationsImpl(appContext.getString(R.string.appName), gdCredentials)
}