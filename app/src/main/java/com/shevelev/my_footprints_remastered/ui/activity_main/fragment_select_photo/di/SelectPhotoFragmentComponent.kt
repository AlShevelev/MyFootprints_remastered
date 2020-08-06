package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.SelectPhotoFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [SelectPhotoFragmentModuleBinds::class])
@SubFragmentScope
interface SelectPhotoFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SelectPhotoFragmentComponent
    }

    fun inject(fragment: SelectPhotoFragment)
}