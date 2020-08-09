package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.CropPhotoFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent
@SubFragmentScope
interface CropPhotoFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): CropPhotoFragmentComponent
    }

    fun inject(fragment: CropPhotoFragment)
}