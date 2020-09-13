package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import javax.inject.Inject

class SetLocationFragmentModelImpl
@Inject
constructor(
    private val appContext: Context
): ModelBaseImpl(), SetLocationFragmentModel  {
    override val isGooglePlayServicesAvailable: Boolean
        get() = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appContext) == ConnectionResult.SUCCESS
}