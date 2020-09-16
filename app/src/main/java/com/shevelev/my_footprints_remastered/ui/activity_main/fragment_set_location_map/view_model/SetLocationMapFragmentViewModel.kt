package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view_model

import android.graphics.Color
import androidx.annotation.ColorInt
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.model.SetLocationMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view.ButtonsBindingCall
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.InitMapUserData
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowColorDialog
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowMessageRes
import com.shevelev.my_footprints_remastered.ui.view_commands.StartLoadingMap
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

    init {
        sendCommand(StartLoadingMap())

        launch {
            model.locationProvider.lastLocationFlow.collect {
                // Update pin location if it was not located manually
            }
        }
    }

    fun mapLoaded() = initMap()

    override fun onColorDialogButtonClick() {
        sendCommand(ShowColorDialog(model.pinTextColor, model.pinBackgroundColor))
    }

    override fun onHelpButtonClick() {
        // do noting so far
    }

    fun onPinColorSelected(@ColorInt textColor: Int, @ColorInt backgroundColor: Int) {
        model.pinTextColor = textColor
        model.pinBackgroundColor = backgroundColor
        initMap()
    }

    private fun initMap() {
        launch {
            try {
                sendCommand(InitMapUserData(startZoom, model.locationProvider.lastLocation, model.getPinInfo()))
            } catch (ex: Exception) {
                sendCommand(ShowMessageRes(R.string.generalError))
            }
        }
    }
}