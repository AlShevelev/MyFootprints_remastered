package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.BuildConfig
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.model.SettingsFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.OpenEmail
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragmentViewModel
@Inject
constructor(
    private val appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SettingsFragmentModel
) : ViewModelBase<SettingsFragmentModel>(dispatchersProvider, model) {

    val title = appContext.getString(R.string.settings)

    val version = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

    private val _isUseWiFiToLoadGeoData = MutableLiveData(true)
    val isUseWiFiToLoadGeoData: LiveData<Boolean> = _isUseWiFiToLoadGeoData

    private val _isLoadGeoOnFootprintCreate = MutableLiveData(true)
    val isLoadGeoOnFootprintCreate: LiveData<Boolean> = _isLoadGeoOnFootprintCreate

    private val _isUseWiFiToBackup = MutableLiveData(true)
    val isUseWiFiToBackup: LiveData<Boolean> = _isUseWiFiToBackup

    init {
        launch {
            _isUseWiFiToLoadGeoData.value = model.isUseWiFiToLoadGeoData()
            _isLoadGeoOnFootprintCreate.value = model.isLoadGeoOnFootprintCreate()
            _isUseWiFiToBackup.value = model.isUseWiFiToBackup()
        }
    }

    fun onBackClick() = sendCommand(MoveBack())

    fun onLoadWifiClick() {
        launch {
            _isUseWiFiToLoadGeoData.value = model.setUseWiFiToLoadGeoData()
        }
    }

    fun onLoadGeoClick() {
        launch {
            _isLoadGeoOnFootprintCreate.value = model.setLoadGeoOnFootprintCreate()
        }
    }

    fun onBackupWifiClick() {
        launch {
            _isUseWiFiToBackup.value = model.setUseWiFiToBackup()
        }
    }

    fun onEmailClick() = sendCommand(OpenEmail(appContext.getString(R.string.contactEmail)))
}