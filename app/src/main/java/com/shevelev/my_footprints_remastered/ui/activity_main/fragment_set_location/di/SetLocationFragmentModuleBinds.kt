package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view_model.SetLocationFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SetLocationFragmentModuleBinds {
    @Binds
    @IntoMap
    @ViewModelKey(SetLocationFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: SetLocationFragmentViewModel): ViewModel
}