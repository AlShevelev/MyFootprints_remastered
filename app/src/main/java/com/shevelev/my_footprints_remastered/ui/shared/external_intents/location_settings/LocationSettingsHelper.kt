package com.shevelev.my_footprints_remastered.ui.shared.external_intents.location_settings

import androidx.fragment.app.Fragment

interface LocationSettingsHelper {
    fun openSettings(fragment: Fragment): Boolean
}