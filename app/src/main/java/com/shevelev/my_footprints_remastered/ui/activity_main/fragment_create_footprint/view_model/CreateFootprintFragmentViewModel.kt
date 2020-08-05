package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class CreateFootprintFragmentViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintFragmentModel
) : ViewModelBase<CreateFootprintFragmentModel>(dispatchersProvider, model) {

    val title = appContext.getString(R.string.createFootprint)

    fun onBackButtonClick() {
        _command.value = MoveBack()
    }
}