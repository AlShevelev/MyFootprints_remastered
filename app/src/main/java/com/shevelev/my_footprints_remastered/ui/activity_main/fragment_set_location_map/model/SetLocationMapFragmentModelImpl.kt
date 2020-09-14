package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model

import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import javax.inject.Inject

class SetLocationMapFragmentModelImpl
@Inject
constructor(
    override val locationProvider: GeolocationProviderData
) : ModelBaseImpl(),
    SetLocationMapFragmentModel