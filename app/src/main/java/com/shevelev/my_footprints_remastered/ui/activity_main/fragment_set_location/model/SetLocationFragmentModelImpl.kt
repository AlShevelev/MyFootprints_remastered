package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import javax.inject.Inject

class SetLocationFragmentModelImpl
@Inject
constructor(
    private val appContext: Context,
    private val sharedFootprint: SharedFootprint
): ModelBaseImpl(), SetLocationFragmentModel  {
    override val isGooglePlayServicesAvailable: Boolean
        get() = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appContext) == ConnectionResult.SUCCESS

    override val canSave: Boolean
        get() = sharedFootprint.image != null
}