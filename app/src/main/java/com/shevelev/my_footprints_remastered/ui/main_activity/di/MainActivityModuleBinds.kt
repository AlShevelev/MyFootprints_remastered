package com.shevelev.my_footprints_remastered.ui.main_activity.di

import com.shevelev.my_footprints_remastered.ui.main_activity.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.main_activity.navigation.MainActivityNavigationImpl
import com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater.TitleDataUpdaterConsumer
import com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater.TitleDataUpdaterImpl
import com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater.TitleDataUpdaterProvider
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
    abstract fun provideTitleDataUpdaterForProvider(updater: TitleDataUpdaterImpl): TitleDataUpdaterProvider

    @Binds
    @ActivityScope
    abstract fun provideTitleDataUpdaterForConsumer(updater: TitleDataUpdaterImpl): TitleDataUpdaterConsumer

    @Binds
    @ActivityScope
    abstract fun provideMainActivityNavigation(navigation: MainActivityNavigationImpl): MainActivityNavigation
}