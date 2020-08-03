package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.di

import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.SelectPhotoFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [SelectPhotoFragmentModuleBinds::class])
@FragmentScope
interface SelectPhotoFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SelectPhotoFragmentComponent
    }

    fun inject(fragment: SelectPhotoFragment)
}