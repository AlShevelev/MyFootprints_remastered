package com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned

import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ListItem

/**
 * To make Diff calculations much move simple
 */
interface VersionedListItem : ListItem {
    val version: Long

    val isFirstItem: Boolean
    val isLastItem: Boolean
}