package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.model.SettingsFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.model.SettingsFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.view_model.SettingsFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.email.SendEmailHelper
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.email.SendEmailHelperImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(SettingsFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: SettingsFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: SettingsFragmentModelImpl): SettingsFragmentModel

    @Binds
    abstract fun provideSendEmailHelper(helper: SendEmailHelperImpl): SendEmailHelper
}