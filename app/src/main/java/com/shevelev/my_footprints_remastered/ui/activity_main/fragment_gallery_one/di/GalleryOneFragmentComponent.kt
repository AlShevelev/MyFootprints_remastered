package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view.GalleryOneFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [GalleryOneFragmentModuleBinds::class])
@FragmentScope
interface GalleryOneFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): GalleryOneFragmentComponent
    }

    fun inject(fragment: GalleryOneFragment)
}