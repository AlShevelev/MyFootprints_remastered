package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.di

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.view.GoogleDriveSignInFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Suppress("EXPERIMENTAL_API_USAGE")
@Subcomponent(modules = [GoogleDriveSignInFragmentModuleBinds::class])
@FragmentScope
interface GoogleDriveSignInFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): GoogleDriveSignInFragmentComponent
    }

    fun inject(fragment: GoogleDriveSignInFragment)
}