package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.model.SelectPhotoFragmentModel
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.model.SelectPhotoFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model.SelectPhotoFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SelectPhotoFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(SelectPhotoFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: SelectPhotoFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: SelectPhotoFragmentModelImpl): SelectPhotoFragmentModel
}