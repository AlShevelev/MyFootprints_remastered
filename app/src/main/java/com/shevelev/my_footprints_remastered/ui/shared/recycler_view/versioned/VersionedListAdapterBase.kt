package com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned

import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.DiffAlgBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListAdapterBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListItemEventsProcessor

abstract class VersionedListAdapterBase<TListItemEventsProcessor: ListItemEventsProcessor>(
    listItemEventsProcessor: TListItemEventsProcessor,
    pageSize: Int?
) : ListAdapterBase<TListItemEventsProcessor, VersionedListItem>(
    listItemEventsProcessor,
    pageSize) {

    override fun createDiffAlg(oldData: List<VersionedListItem>, newData: List<VersionedListItem>): DiffAlgBase<VersionedListItem> =
        VersionedDiffAlg(oldData, newData)
}