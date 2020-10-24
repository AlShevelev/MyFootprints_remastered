package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface SettingsFragmentModel : ModelBase {
    suspend fun isUseWiFiToLoadGeoData(): Boolean

    suspend fun setUseWiFiToLoadGeoData(): Boolean
}