package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view_model

import android.location.Location
import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.dto.PinInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model.SetLocationMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view.ButtonsBindingCall
import com.shevelev.my_footprints_remastered.ui.shared.Constants
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

    private val startZoom = Constants.MAP_START_ZOOM

    private val _pin = MutableLiveData<PinInfo>()
    val pin: LiveData<PinInfo> = _pin

    init {
        launch {
            model.lastLocationFlow.collect {
                if(model.manualLocation == null && it != null) {
                    _pin.value = PinInfo(it, model.pinInfo ?: model.updatePinInfo())
                }
            }
        }
    }

    fun onViewCreated() {
        sendCommand(StartLoadingMap)
    }

    fun mapLoaded() {
        launch {
            try {
                sendCommand(MoveAndZoomMap(startZoom, model.lastLocation))
                _pin.value = PinInfo(model.lastLocation, model.pinInfo ?: model.updatePinInfo())
            } catch (ex: Exception) {
                sendCommand(ShowMessageRes(R.string.generalError))
            }
        }
    }

    override fun onColorDialogButtonClick() {
        sendCommand(ShowColorDialog(model.pinColor.textColor, model.pinColor.backgroundColor))
    }

    override fun onHelpButtonClick() = sendCommand(ShowMessageRes(R.string.mapPinHelp))

    fun onPinColorSelected(@ColorInt textColor: Int, @ColorInt backgroundColor: Int) {
        model.pinColor = PinColor(textColor, backgroundColor)

        launch {
            try {
                _pin.value = PinInfo(model.lastLocation, model.updatePinInfo())
            } catch (ex: Exception) {
                sendCommand(ShowMessageRes(R.string.generalError))
            }
        }
    }

    fun onMapLongClick(location: Location) {
        model.manualLocation = location
        _pin.value = PinInfo(model.lastLocation, model.pinInfo!!)
    }
}