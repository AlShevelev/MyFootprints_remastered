package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view_model

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.dto.PinInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model.SetLocationMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view.ButtonsBindingCall
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetLocationMapFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: SetLocationMapFragmentModel
) : ViewModelBase<SetLocationMapFragmentModel>(dispatchersProvider, model),
    ButtonsBindingCall {

    private val startZoom = 18f

    private val _pin = MutableLiveData<PinInfo>()
    val pin: LiveData<PinInfo> = _pin

    init {
        launch {
            model.locationProvider.lastLocationFlow.collect {
                // Update pin location if it was not located manually
            }
        }
    }

    fun onViewCreated() {
        sendCommand(StartLoadingMap())
    }

    fun mapLoaded() {
        launch {
            try {
                sendCommand(MoveAndZoomMap(startZoom, model.locationProvider.lastLocation))
                _pin.value = PinInfo(model.locationProvider.lastLocation, model.getPinInfo())
            } catch (ex: Exception) {
                sendCommand(ShowMessageRes(R.string.generalError))
            }
        }
    }

    override fun onColorDialogButtonClick() {
        sendCommand(ShowColorDialog(model.pinTextColor, model.pinBackgroundColor))
    }

    override fun onHelpButtonClick() {
        // do noting so far
    }

    fun onPinColorSelected(@ColorInt textColor: Int, @ColorInt backgroundColor: Int) {
        model.pinTextColor = textColor
        model.pinBackgroundColor = backgroundColor

        launch {
            try {
                _pin.value = PinInfo(model.locationProvider.lastLocation, model.getPinInfo())
            } catch (ex: Exception) {
                sendCommand(ShowMessageRes(R.string.generalError))
            }
        }
    }
}