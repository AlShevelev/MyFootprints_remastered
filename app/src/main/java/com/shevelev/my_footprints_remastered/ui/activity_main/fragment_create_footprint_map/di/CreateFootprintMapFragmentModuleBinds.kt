package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.model.CreateFootprintMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.model.CreateFootprintMapFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.view_model.CreateFootprintMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreateFootprintMapFragmentModuleBinds {
    @Binds
    @IntoMap
    @ViewModelKey(CreateFootprintMapFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: CreateFootprintMapFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: CreateFootprintMapFragmentModelImpl): CreateFootprintMapFragmentModel
}