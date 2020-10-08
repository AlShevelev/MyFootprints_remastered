package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.model.MyWorldFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.model.MyWorldFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.view_model.MyWorldFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyWorldFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(MyWorldFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: MyWorldFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: MyWorldFragmentModelImpl): MyWorldFragmentModel

}