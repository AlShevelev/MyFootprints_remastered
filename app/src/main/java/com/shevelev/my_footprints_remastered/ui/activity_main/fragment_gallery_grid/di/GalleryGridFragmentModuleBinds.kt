package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model.GalleryGridFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model.GalleryGridFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model.GalleryGridFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GalleryGridFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(GalleryGridFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: GalleryGridFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: GalleryGridFragmentModelImpl): GalleryGridFragmentModel
}