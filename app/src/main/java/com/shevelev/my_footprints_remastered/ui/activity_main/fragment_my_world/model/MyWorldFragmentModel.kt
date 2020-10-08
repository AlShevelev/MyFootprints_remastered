package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface MyWorldFragmentModel : ModelBase {
    val isGooglePlayServicesAvailable: Boolean
}