package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.EditPhotoFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent
@SubFragmentScope
interface EditPhotoFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): EditPhotoFragmentComponent
    }

    fun inject(fragment: EditPhotoFragment)
}