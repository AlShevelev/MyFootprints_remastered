package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface CreateFootprintFragmentModel : ModelBase {
    suspend fun processNewPhotoSelected(callbackAction: (SelectedPhotoLoadingState) -> Unit)

    suspend fun clearPhoto()
}