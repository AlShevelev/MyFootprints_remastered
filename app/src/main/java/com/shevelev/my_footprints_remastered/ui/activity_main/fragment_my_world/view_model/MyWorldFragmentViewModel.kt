package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.view_model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.model.MyWorldFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.AddMapFragment
import com.shevelev.my_footprints_remastered.ui.view_commands.AddStubFragment
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class MyWorldFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: MyWorldFragmentModel
) : ViewModelBase<MyWorldFragmentModel>(dispatchersProvider, model) {

    init {
        sendCommand(if(model.isGooglePlayServicesAvailable) AddMapFragment() else AddStubFragment())
    }

    fun onBackClick() = sendCommand(MoveBack())
}