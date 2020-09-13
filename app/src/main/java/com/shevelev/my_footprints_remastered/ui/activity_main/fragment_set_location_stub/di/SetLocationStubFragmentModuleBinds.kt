package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.model.SetLocationStubFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.model.SetLocationStubFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.view_model.SetLocationStubFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SetLocationStubFragmentModuleBinds {
    @Binds
    @IntoMap
    @ViewModelKey(SetLocationStubFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: SetLocationStubFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: SetLocationStubFragmentModelImpl): SetLocationStubFragmentModel
}