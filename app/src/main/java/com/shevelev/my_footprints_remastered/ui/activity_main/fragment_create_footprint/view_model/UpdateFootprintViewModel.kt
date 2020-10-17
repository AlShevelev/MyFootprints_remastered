package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model

import android.content.Context
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets.PhotoContainerState
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToSetLocation
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateFootprintViewModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: CreateFootprintFragmentModel,
    private val oldFootprint: Footprint
) : CreateFootprintFragmentViewModel(
    dispatchersProvider,
    model) {

    override val title: String = appContext.getString(R.string.updateFootprint)

    init {
        launch {
            model.initSharedFootprint()
            comment.value = model.sharedFootprint.comment
            _containerState.value = PhotoContainerState.Ready(model.sharedFootprint.image!!)
            _saveEnabled.value = model.canSave
        }

        comment.observeForever {
            model.sharedFootprint.comment = it
        }
    }

    override fun onMoveToMapClick() = sendCommand(MoveToSetLocation(oldFootprint, model.isImageUpdated))
}