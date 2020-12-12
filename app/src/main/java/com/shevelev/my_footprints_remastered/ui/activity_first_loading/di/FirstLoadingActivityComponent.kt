package com.shevelev.my_footprints_remastered.ui.activity_first_loading.di

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.FirstLoadingActivity
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.di.GoogleDriveSignInFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.di.LoadingProgressFragmentComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import dagger.Subcomponent

@Subcomponent(modules = [
    FirstLoadingActivityModuleBinds::class,
    FirstLoadingActivityModuleChilds::class])
@ActivityScope
interface FirstLoadingActivityComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): FirstLoadingActivityComponent
    }

    val googleDriveSignInFragment: GoogleDriveSignInFragmentComponent.Builder
    val loadingProgressFragment: LoadingProgressFragmentComponent.Builder

    fun inject(activity: FirstLoadingActivity)
}