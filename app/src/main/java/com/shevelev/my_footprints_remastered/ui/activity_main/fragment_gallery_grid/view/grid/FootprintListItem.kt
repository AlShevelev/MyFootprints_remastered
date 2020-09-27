package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

data class FootprintListItem(
    override val id: Long,
    override val version: Long,
    override val isFirstItem: Boolean,
    override val isLastItem: Boolean,
    val footprint: Footprint
) : VersionedListItem