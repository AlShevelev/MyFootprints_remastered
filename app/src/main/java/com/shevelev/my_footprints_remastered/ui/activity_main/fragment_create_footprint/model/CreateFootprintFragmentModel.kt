package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import java.io.File

interface CreateFootprintFragmentModel : ModelBase {
    val geolocationProvider: GeolocationProviderManager

    val sharedFootprint: SharedFootprint

    val canSave: Boolean

    suspend fun initSharedFootprint()

    suspend fun processNewPhotoSelected(callbackAction: (SelectedPhotoLoadingState) -> Unit)

    suspend fun clearPhoto()

    suspend fun save()

    suspend fun removeDraftFootprint()
}