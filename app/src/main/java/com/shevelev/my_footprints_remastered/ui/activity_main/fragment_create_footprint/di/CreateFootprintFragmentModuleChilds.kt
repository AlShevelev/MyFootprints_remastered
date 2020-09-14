package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di.SetLocationFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.di.CropPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.di.EditPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.di.SetLocationMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di.SetLocationStubFragmentComponent
import dagger.Module

@Module(subcomponents = [
    SelectPhotoFragmentComponent::class,
    CropPhotoFragmentComponent::class,
    EditPhotoFragmentComponent::class,
    SetLocationFragmentComponent::class,
    SetLocationStubFragmentComponent::class,
    SetLocationMapFragmentComponent::class])
class CreateFootprintFragmentModuleChilds