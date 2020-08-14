package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import java.io.File

interface CreateFootprintFragmentModel : ModelBase {
    val photoImage: File?

    suspend fun processNewPhotoSelected(callbackAction: (SelectedPhotoLoadingState) -> Unit)

    suspend fun clearPhoto()
}