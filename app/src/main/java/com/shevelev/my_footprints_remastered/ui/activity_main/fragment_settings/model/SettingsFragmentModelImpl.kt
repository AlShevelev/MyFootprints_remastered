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

    private var _isUseWiFiToLoadGeoData: Boolean? = null

    override suspend fun isUseWiFiToLoadGeoData(): Boolean {
        if(_isUseWiFiToLoadGeoData == null) {
            _isUseWiFiToLoadGeoData = withContext(dispatchersProvider.ioDispatcher) {
                keyValueStorageFacade.isUseWiFiToLoadGeoData()
            }
        }
        return _isUseWiFiToLoadGeoData!!
    }

    override suspend fun setUseWiFiToLoadGeoData(): Boolean =
        _isUseWiFiToLoadGeoData!!.let {
            withContext(dispatchersProvider.ioDispatcher) {
                keyValueStorageFacade.setUseWiFiToLoadGeoData(!it)
            }
            _isUseWiFiToLoadGeoData = !it
            !it
        }
}