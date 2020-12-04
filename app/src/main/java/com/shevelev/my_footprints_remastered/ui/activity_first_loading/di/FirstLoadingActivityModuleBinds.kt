package com.shevelev.my_footprints_remastered.ui.activity_first_loading.di

import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentialsImpl
import dagger.Binds
import dagger.Module

@Module
abstract class FirstLoadingActivityModuleBinds {
    @Binds
    abstract fun provideGoogleDriveCredentials(credentials: GoogleDriveCredentialsImpl): GoogleDriveCredentials
}