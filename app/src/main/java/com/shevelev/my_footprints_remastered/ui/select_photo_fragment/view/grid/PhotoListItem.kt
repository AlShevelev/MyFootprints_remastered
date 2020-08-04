package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view.grid

import android.net.Uri
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

data class PhotoListItem(
    override val id: Long,
    override val version: Long,
    override val isFirstItem: Boolean,
    override val isLastItem: Boolean,

    val imageUri: Uri
) : VersionedListItem