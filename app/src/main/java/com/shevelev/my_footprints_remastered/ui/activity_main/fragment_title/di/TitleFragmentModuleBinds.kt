package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.sync.sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.sync.sign_in.GoogleDriveCredentialsImpl
import com.shevelev.my_footprints_remastered.sync.sign_in.GoogleDriveSignIn
import com.shevelev.my_footprints_remastered.sync.sign_in.GoogleDriveSignInImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model.TitleFragmentViewModel
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Module
abstract class TitleFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(TitleFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: TitleFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: TitleFragmentModelImpl): TitleFragmentModel

    @Binds
    abstract fun provideGoogleDriveCredentials(credentials: GoogleDriveCredentialsImpl): GoogleDriveCredentials

    @Binds
    @FragmentScope
    abstract fun provideGoogleDriveSignIn(signIn: GoogleDriveSignInImpl): GoogleDriveSignIn
}