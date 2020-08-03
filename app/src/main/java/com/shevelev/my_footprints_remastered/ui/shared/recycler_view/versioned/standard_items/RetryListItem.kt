package com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.standard_items

import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

data class RetryListItem(
    override val id: Long = IdUtil.generateLongId(),
    override val version: Long = 0,
    override val isFirstItem: Boolean = false,
    override val isLastItem: Boolean = false
): VersionedListItem