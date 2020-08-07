package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridgeImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.CreateFootprintFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model.SelectPhotoFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreateFootprintFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(CreateFootprintFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: CreateFootprintFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: CreateFootprintFragmentModelImpl): CreateFootprintFragmentModel

    @Binds
    @FragmentScope
    abstract fun provideDataBridge(bridge: CreateFootprintFragmentDataBridgeImpl): CreateFootprintFragmentDataBridge
}