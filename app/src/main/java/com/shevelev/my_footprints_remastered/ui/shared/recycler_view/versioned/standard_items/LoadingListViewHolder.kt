package com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.standard_items

import android.view.View
import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import kotlinx.android.synthetic.main.list_item_loading.view.*

class LoadingListViewHolder<TEventsProcessor: ListItemEventsProcessor>(
    parentView: ViewGroup
) : ViewHolderBase<TEventsProcessor, LoadingListItem>(
    parentView,
    R.layout.list_item_loading
) {
    override fun init(listItem: LoadingListItem, listItemEventsProcessor: TEventsProcessor) {
        itemView.loading.visibility = View.VISIBLE
    }
}