package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.model.GalleryOneFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.model.GalleryOneFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view_model.GalleryOneFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GalleryOneFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(GalleryOneFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: GalleryOneFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: GalleryOneFragmentModelImpl): GalleryOneFragmentModel
}