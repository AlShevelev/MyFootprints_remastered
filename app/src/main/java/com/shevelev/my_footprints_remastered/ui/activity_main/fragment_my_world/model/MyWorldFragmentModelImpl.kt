package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.model

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import javax.inject.Inject

class MyWorldFragmentModelImpl
@Inject
constructor(
    private val appContext: Context
) : ModelBaseImpl(), MyWorldFragmentModel {
    override val isGooglePlayServicesAvailable: Boolean
        get() = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appContext) == ConnectionResult.SUCCESS
}