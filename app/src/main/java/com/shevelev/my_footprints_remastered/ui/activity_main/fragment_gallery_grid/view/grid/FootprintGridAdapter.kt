package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid

import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model.FootprintListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListAdapterBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

class FootprintGridAdapter(
    listItemEventsProcessor: FootprintListItemEventsProcessor
) : VersionedListAdapterBase<FootprintListItemEventsProcessor>(listItemEventsProcessor, null) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<FootprintListItemEventsProcessor, VersionedListItem> =
        FootprintViewHolder(parent) as ViewHolderBase<FootprintListItemEventsProcessor, VersionedListItem>
}