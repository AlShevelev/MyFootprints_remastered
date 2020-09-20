package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface SetLocationFragmentModel : ModelBase {
    val isGooglePlayServicesAvailable: Boolean

    val canSave: Boolean

    suspend fun save()
}