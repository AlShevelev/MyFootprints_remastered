package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model.SetLocationMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model.SetLocationMapFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view_model.SetLocationMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SetLocationMapFragmentModuleBinds {
    @Binds
    @IntoMap
    @ViewModelKey(SetLocationMapFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: SetLocationMapFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: SetLocationMapFragmentModelImpl): SetLocationMapFragmentModel

    @Binds
    abstract fun providePinDraw(pinDraw: PinDrawImpl): PinDraw
}