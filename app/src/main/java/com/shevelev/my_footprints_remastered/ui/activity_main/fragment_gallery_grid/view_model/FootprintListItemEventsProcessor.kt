package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model

import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListItemEventsProcessor

interface FootprintListItemEventsProcessor: ListItemEventsProcessor {
    fun onFootprintClick(id: Long)
}