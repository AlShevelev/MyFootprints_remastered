package com.shevelev.my_footprints_remastered.ui.activity_main.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigationImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderApp
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderImpl
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawImpl
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Module
abstract class MainActivityModuleBinds {
    @Binds
    @ActivityScope
    abstract fun provideTitleDataUpdaterForProvider(flow: LastFootprintDataFlowImpl): LastFootprintDataFlowProvider

    @Binds
    @ActivityScope
    abstract fun provideTitleDataUpdaterForConsumer(flow: LastFootprintDataFlowImpl): LastFootprintDataFlowConsumer

    @Binds
    @ActivityScope
    abstract fun provideUpdateFootprintProvider(flow: UpdateFootprintDataFlowImpl): UpdateFootprintDataFlowProvider

    @Binds
    @ActivityScope
    abstract fun provideUpdateFootprintConsumer(flow: UpdateFootprintDataFlowImpl): UpdateFootprintDataFlowConsumer

    @Binds
    @ActivityScope
    abstract fun provideDeleteFootprintProvider(flow: DeleteFootprintDataFlowImpl): DeleteFootprintDataFlowProvider

    @Binds
    @ActivityScope
    abstract fun provideDeleteFootprintConsumer(flow: DeleteFootprintDataFlowImpl): DeleteFootprintDataFlowConsumer

    @Binds
    @ActivityScope
    abstract fun provideMainActivityNavigation(navigation: MainActivityNavigationImpl): MainActivityNavigation

    @Binds
    @ActivityScope
    abstract fun provideGeolocationProviderManager(provider: GeolocationProviderImpl): GeolocationProviderManager

    @Binds
    @ActivityScope
    abstract fun provideGeolocationProviderApp(provider: GeolocationProviderImpl): GeolocationProviderApp

    @Binds
    @ActivityScope
    abstract fun provideGeolocationProviderFlow(provider: GeolocationProviderImpl): GeolocationProviderData


    @Binds
    abstract fun providePinDraw(pinDraw: PinDrawImpl): PinDraw
}