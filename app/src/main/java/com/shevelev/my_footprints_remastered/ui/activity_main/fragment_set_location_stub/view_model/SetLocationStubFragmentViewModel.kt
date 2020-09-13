package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.view_model

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.model.SetLocationStubFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.resources.getStringFormatted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SetLocationStubFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SetLocationStubFragmentModel
) : ViewModelBase<SetLocationStubFragmentModel>(dispatchersProvider, model) {

    private val _lastLocation = MutableLiveData<String>()
    val lastLocation: LiveData<String> = _lastLocation

    init {
        launch {
            model.locationProvider.lastLocationFlow.collect {
                Timber.tag("DDDDD").d("Collect")
                _lastLocation.value = appContext.getStringFormatted(
                    R.string.currentLocationIs,
                    Location.convert(it.latitude, Location.FORMAT_DEGREES),
                    Location.convert(it.longitude, Location.FORMAT_DEGREES)
                )
            }
        }
    }
}