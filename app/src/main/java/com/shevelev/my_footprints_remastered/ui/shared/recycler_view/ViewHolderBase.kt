package com.shevelev.my_footprints_remastered.ui.shared.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolderBase<TEventsProcessor: ListItemEventsProcessor, TItem: ListItem>(
    parentView: ViewGroup,
    @LayoutRes layoutResId: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context).inflate(layoutResId, parentView, false)
) {
    /**
     * UI elements must be initialized here
     */
    abstract fun init(listItem: TItem, listItemEventsProcessor: TEventsProcessor)

    /**
     * Used resources of UI elements must be released here (called in ListAdapterBase::onViewRecycled)
     */
    open fun release() {}
}