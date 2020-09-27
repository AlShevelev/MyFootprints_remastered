package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.GalleryGridFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [GalleryGridFragmentModuleBinds::class])
@FragmentScope
interface GalleryGridFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): GalleryGridFragmentComponent
    }

    fun inject(fragment: GalleryGridFragment)
}