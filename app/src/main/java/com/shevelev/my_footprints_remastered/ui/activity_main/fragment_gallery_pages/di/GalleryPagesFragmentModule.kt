package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.di

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import dagger.Module
import dagger.Provides

@Module
class GalleryPagesFragmentModule(
    private val footprints: List<Footprint>,
    private val currentFootprintIndex: Int
) {
    @Provides
    internal fun provideFootprints(): List<Footprint>  = footprints

    @Provides
    internal fun provideCurrentFootprintIndex(): Int  = currentFootprintIndex
}