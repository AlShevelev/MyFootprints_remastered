package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets.AddPhotoStubBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeaderBindingCall
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToSelectPhoto
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class CreateFootprintFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintFragmentModel
) : ViewModelBase<CreateFootprintFragmentModel>(dispatchersProvider, model),
    AddPhotoStubBindingCall,
    ScreenHeaderBindingCall {

    val title = appContext.getString(R.string.createFootprint)

    override fun onBackClick() {
        sendCommand(MoveBack())
    }

    override fun onAddPhotoClick() {
        sendCommand(MoveToSelectPhoto())
    }
}