package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprintImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model.GalleryPagesFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model.GalleryPagesFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view_model.GalleryPagesFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.share.SharingHelper
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.share.SharingHelperImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GalleryPagesFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(GalleryPagesFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: GalleryPagesFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: GalleryPagesFragmentModelImpl): GalleryPagesFragmentModel

    @Binds
    abstract fun provideCreateEditFootprint(createUpdate: CreateUpdateFootprintImpl): CreateUpdateFootprint

    @Binds
    abstract fun provideSharingHelper(helper: SharingHelperImpl): SharingHelper
}