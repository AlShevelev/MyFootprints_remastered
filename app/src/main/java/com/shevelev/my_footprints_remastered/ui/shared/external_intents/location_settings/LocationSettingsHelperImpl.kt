package com.shevelev.my_footprints_remastered.ui.shared.external_intents.location_settings

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import javax.inject.Inject

class LocationSettingsHelperImpl
@Inject
constructor(
    private val appContext: Context
) : LocationSettingsHelper {
    companion object {
        private const val REQUEST = 9837
    }

    override fun openSettings(fragment: Fragment): Boolean {
        val locationSettingsIntent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)

        if (locationSettingsIntent.resolveActivity(appContext.packageManager) == null) {
            return false
        }

        fragment.startActivity(locationSettingsIntent)
        return true
    }
}