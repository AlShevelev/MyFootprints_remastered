package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di.SetLocationFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.di.CropPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.di.EditPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.di.SetLocationMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di.SetLocationStubFragmentComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [
    CreateFootprintFragmentModuleBinds::class,
    CreateFootprintFragmentModuleChilds::class,
    CreateFootprintFragmentModule::class])
@FragmentScope
interface CreateFootprintFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): CreateFootprintFragmentComponent
    }

    val selectPhotoFragment: SelectPhotoFragmentComponent.Builder
    val cropPhotoFragment: CropPhotoFragmentComponent.Builder
    val editPhotoFragment: EditPhotoFragmentComponent.Builder
    val setLocationFragment: SetLocationFragmentComponent.Builder
    val setLocationStubFragment: SetLocationStubFragmentComponent.Builder
    val setLocationMapFragment: SetLocationMapFragmentComponent.Builder

    fun inject(fragment: CreateFootprintFragment)
}