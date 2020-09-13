package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.model

import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderFlow
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import javax.inject.Inject

class SetLocationStubFragmentModelImpl
@Inject
constructor(
    override val locationProvider: GeolocationProviderFlow
) : ModelBaseImpl(),
    SetLocationStubFragmentModel