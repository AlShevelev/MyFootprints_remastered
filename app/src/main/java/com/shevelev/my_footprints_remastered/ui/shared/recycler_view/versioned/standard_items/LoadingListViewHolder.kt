package com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.standard_items

import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase

class LoadingListViewHolder<TEventsProcessor: ListItemEventsProcessor>(
    parentView: ViewGroup
) : ViewHolderBase<TEventsProcessor, LoadingListItem>(
    parentView,
    R.layout.list_item_loading
) {
    override fun init(listItem: LoadingListItem, listItemEventsProcessor: TEventsProcessor) {
        // do nothing
    }
}