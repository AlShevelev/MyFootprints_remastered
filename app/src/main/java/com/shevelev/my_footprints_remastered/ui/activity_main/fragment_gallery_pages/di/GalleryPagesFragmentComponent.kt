package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view.GalleryPagesFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [GalleryPagesFragmentModuleBinds::class, GalleryPagesFragmentModule::class])
@FragmentScope
interface GalleryPagesFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun init(module: GalleryPagesFragmentModule): Builder
        fun build(): GalleryPagesFragmentComponent
    }

    fun inject(fragment: GalleryPagesFragment)
}