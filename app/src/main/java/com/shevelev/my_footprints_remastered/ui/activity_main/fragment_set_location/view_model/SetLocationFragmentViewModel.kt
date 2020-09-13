package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model.SetLocationFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.AddMapForSetLocation
import com.shevelev.my_footprints_remastered.ui.view_commands.AddStubForSetLocation
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class SetLocationFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SetLocationFragmentModel
) : ViewModelBase<SetLocationFragmentModel>(dispatchersProvider, model),
    ScreenHeaderBindingCall {

    val title = appContext.getString(R.string.setLocation)

    init {
        sendCommand(if(!model.isGooglePlayServicesAvailable) AddMapForSetLocation() else AddStubForSetLocation())
    }

    override fun onBackClick() = sendCommand(MoveBack())
}