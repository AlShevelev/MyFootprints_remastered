package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di

import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.SelectPhotoFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.SelectPhotoFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.photo_items_source.PhotoItemsSource
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.photo_items_source.PhotoItemsSourceImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model.SelectPhotoFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.camera.CameraHelper
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.camera.CameraHelperImpl
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.gallery.GalleryHelper
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.gallery.GalleryHelperImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SelectPhotoFragmentModuleBinds {
    @Binds
    @IntoMap
    @ViewModelKey(SelectPhotoFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: SelectPhotoFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: SelectPhotoFragmentModelImpl): SelectPhotoFragmentModel

    @Binds
    abstract fun providePhotoItemsSource(source: PhotoItemsSourceImpl): PhotoItemsSource

    @Binds
    abstract fun provideCameraHelper(helper: CameraHelperImpl): CameraHelper

    @Binds
    abstract fun provideGalleryHelper(helper: GalleryHelperImpl): GalleryHelper
}