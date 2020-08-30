package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.view_model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.model.CreateFootprintMapFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class CreateFootprintMapFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintMapFragmentModel
) : ViewModelBase<CreateFootprintMapFragmentModel>(dispatchersProvider, model) {

}