package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

interface GalleryPagesFragmentModel : ModelBase {
    var currentIndex: Int

    suspend fun loadItems(): List<VersionedListItem>

    fun getFootprint(index: Int): Footprint
}