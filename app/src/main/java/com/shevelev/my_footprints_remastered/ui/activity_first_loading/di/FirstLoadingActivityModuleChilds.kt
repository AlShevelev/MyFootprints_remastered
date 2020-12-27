package com.shevelev.my_footprints_remastered.ui.activity_first_loading.di

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.di.GoogleDriveSignInFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.di.LoadingProgressFragmentComponent
import dagger.Module

@Module(subcomponents = [
    GoogleDriveSignInFragmentComponent::class,
    LoadingProgressFragmentComponent::class])
class FirstLoadingActivityModuleChilds