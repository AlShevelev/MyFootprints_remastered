package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentialsImpl
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveSignIn
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveSignInImpl
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.model.GoogleDriveSignInFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.model.GoogleDriveSignInFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.view_model.GoogleDriveSignInFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model.TitleFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Module
@FlowPreview
@ExperimentalCoroutinesApi
abstract class GoogleDriveSignInFragmentModuleBinds {
    @Binds
    @FragmentScope
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(GoogleDriveSignInFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: GoogleDriveSignInFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: GoogleDriveSignInFragmentModelImpl): GoogleDriveSignInFragmentModel

    @Binds
    @FragmentScope
    abstract fun provideGoogleDriveSignIn(signIn: GoogleDriveSignInImpl): GoogleDriveSignIn
}