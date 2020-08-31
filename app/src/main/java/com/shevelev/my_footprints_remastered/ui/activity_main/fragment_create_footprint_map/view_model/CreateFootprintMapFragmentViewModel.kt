package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.model.CreateFootprintMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class CreateFootprintMapFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintMapFragmentModel
) : ViewModelBase<CreateFootprintMapFragmentModel>(dispatchersProvider, model),
    ScreenHeaderBindingCall {

    val title = appContext.getString(R.string.setLocation)

    override fun onBackClick() = sendCommand(MoveBack())
}