package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid

import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

data class GalleryListItem(
    override val id: Long,
    override val version: Long,
    override val isFirstItem: Boolean,
    override val isLastItem: Boolean
) : VersionedListItem