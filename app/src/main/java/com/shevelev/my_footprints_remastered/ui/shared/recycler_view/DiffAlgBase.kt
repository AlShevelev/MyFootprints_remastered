package com.shevelev.my_footprints_remastered.ui.shared.recycler_view

import androidx.recyclerview.widget.DiffUtil

abstract class DiffAlgBase<TItem: ListItem>(
    protected val oldList: List<TItem>,
    protected val newList: List<TItem>
) : DiffUtil.Callback() {

    /** */
    override fun getOldListSize(): Int = oldList.size

    /** */
    override fun getNewListSize(): Int = newList.size

    /** */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id
}