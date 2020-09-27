package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

interface GalleryGridFragmentModel : ModelBase {
    suspend fun getItems(): List<VersionedListItem>
}