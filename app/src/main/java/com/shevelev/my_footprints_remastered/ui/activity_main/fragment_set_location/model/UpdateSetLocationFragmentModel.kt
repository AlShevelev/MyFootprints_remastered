package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model

import android.content.Context
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.UpdateFootprintInfo
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateSetLocationFragmentModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    sharedFootprint: SharedFootprint,
    createUpdateFootprint: CreateUpdateFootprint,
    geolocationProvider: GeolocationProviderData,
    lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
    private val oldFootprint: Footprint,
    private val isImageUpdated: Boolean,
    private val updateFootprintDataFlowProvider: UpdateFootprintDataFlowProvider,
    isInUpdateMode: Boolean
) : InsertSetLocationFragmentModel(
    appContext,
    dispatchersProvider ,
    sharedFootprint,
    createUpdateFootprint,
    geolocationProvider,
    lastFootprintDataFlowProvider,
    isInUpdateMode)
, SetLocationFragmentModel  {

    override suspend fun save() {
        val updateInfo = withContext(dispatchersProvider.ioDispatcher) {
            createUpdateFootprint.update(
                UpdateFootprintInfo(
                    draftImageFile = sharedFootprint.image!!,
                    location = sharedFootprint.manualSelectedLocation!!,
                    comment = sharedFootprint.comment,
                    pinColor = sharedFootprint.pinColor,
                    isImageUpdated = isImageUpdated,
                    oldFootprint = oldFootprint
                )
            )
        }

        updateInfo?.let {
            updateFootprintDataFlowProvider.update(it.updatedFootprint)
        }
    }
}