package com.shevelev.my_footprints_remastered.ui.shared.recycler_view

interface AdapterRawDataAccess<TItem: ListItem> {
    fun getItem(position: Int): TItem
}