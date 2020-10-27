package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprintImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridgeImpl
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.location_settings.LocationSettingsHelper
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.location_settings.LocationSettingsHelperImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class CreateFootprintFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @FragmentScope
    abstract fun provideDataBridge(bridge: CreateFootprintFragmentDataBridgeImpl): CreateFootprintFragmentDataBridge

    @Binds
    abstract fun provideLocationSettingsHelper(helper: LocationSettingsHelperImpl): LocationSettingsHelper

    @Binds
    abstract fun provideCreateEditFootprint(createUpdate: CreateUpdateFootprintImpl): CreateUpdateFootprint
}