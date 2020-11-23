package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.model

import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val keyValueStorageFacade: KeyValueStorageFacade
) : ModelBaseImpl(),
    SettingsFragmentModel {

    private var _isUseWiFiToLoadGeoData: Boolean = true
    private var _isLoadGeoOnFootprintCreate: Boolean = true
    private var _isUseWiFiToBackup = true

    override suspend fun isUseWiFiToLoadGeoData(): Boolean {
        _isUseWiFiToLoadGeoData = withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.isUseWiFiToLoadGeoData()
        }
        return _isUseWiFiToLoadGeoData
    }

    override suspend fun setUseWiFiToLoadGeoData(): Boolean {
        _isUseWiFiToLoadGeoData = !_isUseWiFiToLoadGeoData

        withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.setUseWiFiToLoadGeoData(_isUseWiFiToLoadGeoData)
        }

        return _isUseWiFiToLoadGeoData
    }

    override suspend fun isLoadGeoOnFootprintCreate(): Boolean {
        _isLoadGeoOnFootprintCreate = withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.isCanLoadGeoForSingleFootprint()
        }
        return _isLoadGeoOnFootprintCreate
    }

    override suspend fun setLoadGeoOnFootprintCreate(): Boolean {
        _isLoadGeoOnFootprintCreate = !_isLoadGeoOnFootprintCreate

        withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.setCanLoadGeoForSingleFootprint(_isLoadGeoOnFootprintCreate)
        }

        return _isLoadGeoOnFootprintCreate
    }

    override suspend fun isUseWiFiToBackup(): Boolean {
        _isUseWiFiToBackup = withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.isUseWiFiToBackup()
        }
        return _isUseWiFiToBackup
    }

    override suspend fun setUseWiFiToBackup(): Boolean {
        _isUseWiFiToBackup = !_isUseWiFiToBackup

        withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.setUseWiFiToBackup(_isUseWiFiToBackup)
        }

        return _isUseWiFiToBackup
    }
}