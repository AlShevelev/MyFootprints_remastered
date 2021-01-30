package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import android.content.Context
import android.graphics.Color
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.common_entities.UpdateFootprintInfo
import com.shevelev.my_footprints_remastered.image_type_detector.ImageTypeDetector
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.storages.files.BitmapFilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.location.toAndroidLocation
import com.shevelev.my_footprints_remastered.utils.location.toGeoPoint
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateFootprintModel
@Inject
constructor(
    appContext: Context,
    dispatchersProvider: DispatchersProvider,
    dataBridge: CreateFootprintFragmentDataBridge,
    geolocationProvider: GeolocationProviderManager,
    sharedFootprint: SharedFootprint,
    filesHelper: BitmapFilesHelper,
    createUpdateFootprint: CreateUpdateFootprint,
    lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
    keyValueStorageFacade: KeyValueStorageFacade,
    private val oldFootprint: Footprint,
    private val updateFootprintDataFlowProvider: UpdateFootprintDataFlowProvider,
    imageTypeDetector: ImageTypeDetector
) : InsertFootprintModel(
        appContext,
        dispatchersProvider,
        dataBridge,
        geolocationProvider,
        sharedFootprint,
        filesHelper,
        createUpdateFootprint,
        lastFootprintDataFlowProvider,
        keyValueStorageFacade,
        imageTypeDetector
), CreateFootprintFragmentModel {

    override suspend fun initSharedFootprint() {
        val pinColor = withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.loadPinColor()
        } ?: PinColor(Color.WHITE, appContext.getColor(R.color.red))
        sharedFootprint.pinColor = pinColor

        sharedFootprint.comment = oldFootprint.comment
        sharedFootprint.manualSelectedLocation = oldFootprint.location.toAndroidLocation()
        sharedFootprint.image = withContext(dispatchersProvider.ioDispatcher) {
            val source = filesHelper.getOrCreateImageFile(oldFootprint.imageFileName)
            val draft = filesHelper.createTempFile()
            filesHelper.copyFile(source, draft)
            draft
        }
    }

    override suspend fun save() {
        withContext(dispatchersProvider.ioDispatcher) {
            createUpdateFootprint.update(
                UpdateFootprintInfo(
                    draftImageFile = sharedFootprint.image!!,
                    location = (sharedFootprint.manualSelectedLocation ?: geolocationProvider.lastLocation).toGeoPoint(),
                    comment = sharedFootprint.comment,
                    pinColor = sharedFootprint.pinColor,
                    isImageUpdated = isImageUpdated,
                    oldFootprint = oldFootprint
                )
            )
        }
        ?.let { editInfo ->
            updateFootprintDataFlowProvider.update(editInfo.updatedFootprint)
        }
    }
}