package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.di.CreateFootprintMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.di.CropPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.EditPhotoFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.di.EditPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import dagger.Module

@Module(subcomponents = [
    SelectPhotoFragmentComponent::class,
    CropPhotoFragmentComponent::class,
    EditPhotoFragmentComponent::class,
    CreateFootprintMapFragmentComponent::class])
class CreateFootprintFragmentModuleChilds