package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model.SetLocationFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view.ButtonsBindingCall
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SetLocationFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SetLocationFragmentModel
) : ViewModelBase<SetLocationFragmentModel>(dispatchersProvider, model),
    ScreenHeaderBindingCall,
    ButtonsBindingCall {

    val title = appContext.getString(R.string.setLocation)

    val saveEnabled = model.canSave

    init {
        sendCommand(if(model.isGooglePlayServicesAvailable) AddMapForSetLocation() else AddStubForSetLocation())
    }

    override fun onBackClick() = sendCommand(MoveBack())

    override fun onSaveClick() {
        launch {
            try {
                model.save()
                sendCommand(MoveBackFromSetLocationToTitle())
            } catch (ex: Exception) {
                Timber.e(ex)
                sendCommand(ShowMessageRes(R.string.saveFootprintError))
            }
        }
    }
}