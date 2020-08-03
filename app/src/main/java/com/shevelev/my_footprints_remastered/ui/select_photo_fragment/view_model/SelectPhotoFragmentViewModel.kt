package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.model.SelectPhotoFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import javax.inject.Inject

class SelectPhotoFragmentViewModel
@Inject
constructor(
    private val appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: SelectPhotoFragmentModel
) : ViewModelBase<SelectPhotoFragmentModel>(dispatchersProvider, model) {

    val title = appContext.getString(R.string.selectPhoto)

    fun onBackButtonClick() {
        _command.value = MoveBack()
    }
}