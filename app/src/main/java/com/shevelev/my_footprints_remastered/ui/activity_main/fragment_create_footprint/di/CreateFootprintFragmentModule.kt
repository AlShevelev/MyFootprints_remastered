package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CreateFootprintFragmentModule {
    @Provides
    @FragmentScope
    fun provideSharedFootprint() = SharedFootprint()
}