package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

interface SelectPhotoFragmentModel : ModelBase {
    suspend fun getItems(): List<VersionedListItem>
}