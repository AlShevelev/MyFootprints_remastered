package com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.standard_items

import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.RetryListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import kotlinx.android.synthetic.main.list_item_retry.view.*

class RetryListViewHolder<TEventsProcessor: RetryListItemEventsProcessor>(
    parentView: ViewGroup
) : ViewHolderBase<TEventsProcessor, RetryListItem>(
    parentView,
    R.layout.list_item_retry
) {

    override fun init(listItem: RetryListItem, listItemEventsProcessor: TEventsProcessor) {
        itemView.retryButton.setOnClickListener {
            listItemEventsProcessor.onRetryLoadPage()
        }
    }

    override fun release() {
        super.release()
        itemView.retryButton.setOnClickListener(null)
    }
}