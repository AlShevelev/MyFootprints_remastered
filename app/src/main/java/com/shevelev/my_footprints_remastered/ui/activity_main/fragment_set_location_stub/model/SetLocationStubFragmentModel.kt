package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.model

import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderFlow
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface SetLocationStubFragmentModel : ModelBase {
    val locationProvider: GeolocationProviderFlow
}