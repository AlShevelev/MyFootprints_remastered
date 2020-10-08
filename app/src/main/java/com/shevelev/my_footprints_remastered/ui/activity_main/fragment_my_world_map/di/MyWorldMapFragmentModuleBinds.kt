package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model.MyWorldMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model.MyWorldMapFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.pins_cache.PinsCache
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.pins_cache.PinsCacheImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view_model.MyWorldMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyWorldMapFragmentModuleBinds {
    @Binds
    @IntoMap
    @ViewModelKey(MyWorldMapFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: MyWorldMapFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: MyWorldMapFragmentModelImpl): MyWorldMapFragmentModel

    @Binds
    abstract fun providePinsCache(cache: PinsCacheImpl): PinsCache
}