package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.model.GalleryOneFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class GalleryOneFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: GalleryOneFragmentModel
) : ViewModelBase<GalleryOneFragmentModel>(dispatchersProvider, model) {

}