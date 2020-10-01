package com.shevelev.my_footprints_remastered.ui.activity_main.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.di.GalleryGridFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.di.GalleryOneFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.map.MapDialogComponent
import dagger.Module

@Module(subcomponents = [
    TitleFragmentComponent::class,
    SelectPhotoFragmentComponent::class,
    CreateFootprintFragmentComponent::class,
    GalleryGridFragmentComponent::class,
    GalleryOneFragmentComponent::class,
    MapDialogComponent::class])
class MainActivityModuleChilds