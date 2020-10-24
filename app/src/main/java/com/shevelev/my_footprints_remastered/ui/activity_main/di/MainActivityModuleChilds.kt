package com.shevelev.my_footprints_remastered.ui.activity_main.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.di.GalleryGridFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.di.GalleryPagesFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.di.MyWorldFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.di.SettingsFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.map.MapDialogComponent
import dagger.Module

@Module(subcomponents = [
    TitleFragmentComponent::class,
    SelectPhotoFragmentComponent::class,
    CreateFootprintFragmentComponent::class,
    GalleryGridFragmentComponent::class,
    GalleryPagesFragmentComponent::class,
    MapDialogComponent::class,
    MyWorldFragmentComponent::class,
    SettingsFragmentComponent::class])
class MainActivityModuleChilds