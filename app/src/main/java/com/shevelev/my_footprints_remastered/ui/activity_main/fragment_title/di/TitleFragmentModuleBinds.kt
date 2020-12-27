package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model.TitleFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
}