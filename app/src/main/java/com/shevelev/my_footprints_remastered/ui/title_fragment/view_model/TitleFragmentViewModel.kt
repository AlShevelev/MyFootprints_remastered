package com.shevelev.my_footprints_remastered.ui.title_fragment.view_model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.title_fragment.model.TitleFragmentModel
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class TitleFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: TitleFragmentModel
) : ViewModelBase<TitleFragmentModel>(dispatchersProvider, model) {

}