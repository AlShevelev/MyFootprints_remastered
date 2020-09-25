package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.shared_use_cases.CreateEditFootprint
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater.TitleDataUpdaterProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CreateFootprintFragmentModelImpl
@Inject
constructor(
    private val appContext: Context,
    private val dispatchersProvider: DispatchersProvider,
    private val dataBridge: CreateFootprintFragmentDataBridge,
    override val geolocationProvider: GeolocationProviderManager,
    override val sharedFootprint: SharedFootprint,
    private val filesHelper: FilesHelper,
    private val createEditFootprint: CreateEditFootprint,
    private val titleDataUpdaterProvider: TitleDataUpdaterProvider,
    private val keyValueStorageFacade: KeyValueStorageFacade
) : ModelBaseImpl(),
    CreateFootprintFragmentModel {

    override suspend fun initSharedFootprint() {
        val pinColor = withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorageFacade.loadPinColor()
        } ?: PinColor(Color.WHITE, appContext.getColor(R.color.red))
        sharedFootprint.pinColor = pinColor
    }

    override val canSave: Boolean
        get() = sharedFootprint.image != null

    override suspend fun processNewPhotoSelected(callbackAction: (SelectedPhotoLoadingState) -> Unit) {
        val selectedPhotoFile = dataBridge.extractPhotoFile()
        val selectedPhotoUri = dataBridge.extractPhotoUri()
        val selectedPhotoBitmap = dataBridge.extractPhotoBitmap()

        when {
            selectedPhotoFile != null -> {
                sharedFootprint.image = selectedPhotoFile
                callbackAction(SelectedPhotoLoadingState.Ready(sharedFootprint.image!!))
            }
            selectedPhotoUri != null -> {
                callbackAction(SelectedPhotoLoadingState.Loading)

                withContext(dispatchersProvider.ioDispatcher) {
                    sharedFootprint.image = copyUriToFile(selectedPhotoUri)
                }

                callbackAction(SelectedPhotoLoadingState.Ready(sharedFootprint.image!!))
            }
            selectedPhotoBitmap != null -> {
                callbackAction(SelectedPhotoLoadingState.Loading)

                withContext(dispatchersProvider.ioDispatcher) {
                    sharedFootprint.image = copyBitmapToFile(selectedPhotoBitmap)
                }

                callbackAction(SelectedPhotoLoadingState.Ready(sharedFootprint.image!!))
            }
        }
    }

    override suspend fun clearPhoto() {
        withContext(dispatchersProvider.ioDispatcher) {
            sharedFootprint.image?.delete()
        }
        sharedFootprint.image = null
    }

    override suspend fun save() {
        val createInfo = withContext(dispatchersProvider.ioDispatcher) {
            createEditFootprint.create(
                sharedFootprint.image!!,
                sharedFootprint.manualSelectedLocation ?: geolocationProvider.lastLocation,
                sharedFootprint.comment,
                sharedFootprint.pinColor
            )
        }

        titleDataUpdaterProvider.updateLastFootprintUri(createInfo.lastFootprintImage)
        titleDataUpdaterProvider.updateTotalFootprints(createInfo.totalFootprints)
    }

    override suspend fun removeDraftFootprint() {
        withContext(dispatchersProvider.ioDispatcher) {
            createEditFootprint.clearDraft(sharedFootprint.image)
        }
    }

    private fun copyUriToFile(uri: Uri): File =
        filesHelper.createTempFile().also {
            filesHelper.saveUriToFile(uri, it)
        }

    private fun copyBitmapToFile(bitmap: Bitmap): File =
        filesHelper.createTempFile().also {
            filesHelper.saveBitmapToFile(bitmap, it, 75)
        }
}