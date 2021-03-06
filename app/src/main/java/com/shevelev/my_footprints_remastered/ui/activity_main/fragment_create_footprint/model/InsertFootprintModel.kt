package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.image_type_detector.ImageType
import com.shevelev.my_footprints_remastered.image_type_detector.ImageTypeDetector
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.storages.files.BitmapFilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintFlowInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.location.toGeoPoint
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Error
import javax.inject.Inject

open class InsertFootprintModel
@Inject
constructor(
    protected val appContext: Context,
    protected val dispatchersProvider: DispatchersProvider,
    private val dataBridge: CreateFootprintFragmentDataBridge,
    override val geolocationProvider: GeolocationProviderManager,
    override val sharedFootprint: SharedFootprint,
    protected val filesHelper: BitmapFilesHelper,
    protected val createUpdateFootprint: CreateUpdateFootprint,
    private val lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
    protected val keyValueStorageFacade: KeyValueStorageFacade,
    private val imageTypeDetector: ImageTypeDetector
) : ModelBaseImpl(),
    CreateFootprintFragmentModel {

    override var isImageUpdated = false
        protected set

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
                sharedFootprint.image = filesHelper.checkAndCorrectOrientation(selectedPhotoFile)

                isImageUpdated = true
                callbackAction(SelectedPhotoLoadingState.Ready(sharedFootprint.image!!))
            }
            selectedPhotoUri != null -> {
                callbackAction(SelectedPhotoLoadingState.Loading)

                val imageType = withContext(dispatchersProvider.ioDispatcher) {
                    imageTypeDetector.getImageType(appContext, selectedPhotoUri)
                }

                if(imageType == ImageType.UNDEFINED) {
                    callbackAction(SelectedPhotoLoadingState.Error(R.string.imageFormatUnsupported))
                } else {
                    withContext(dispatchersProvider.ioDispatcher) {
                        sharedFootprint.image = copyUriToFile(selectedPhotoUri)
                    }

                    isImageUpdated = true
                    callbackAction(SelectedPhotoLoadingState.Ready(sharedFootprint.image!!))
                }
            }
            selectedPhotoBitmap != null -> {
                callbackAction(SelectedPhotoLoadingState.Loading)

                withContext(dispatchersProvider.ioDispatcher) {
                    sharedFootprint.image = copyBitmapToFile(selectedPhotoBitmap)
                }

                isImageUpdated = true
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
            createUpdateFootprint.create(
                CreateFootprintInfo(
                    draftImageFile = sharedFootprint.image!!,
                    location = (sharedFootprint.manualSelectedLocation ?: geolocationProvider.lastLocation).toGeoPoint(),
                    comment = sharedFootprint.comment,
                    pinColor = sharedFootprint.pinColor
            ))
        }

        lastFootprintDataFlowProvider.update(
            LastFootprintFlowInfo(
            lastFootprintId = createInfo.lastFootprintId,
            lastFootprintFileName = createInfo.lastFootprintImageFileName,
            totalFootprints = createInfo.totalFootprints
        ))
    }

    override suspend fun removeDraftFootprint() {
        withContext(dispatchersProvider.ioDispatcher) {
            createUpdateFootprint.clearDraft(sharedFootprint.image)
        }
    }

    private fun copyUriToFile(uri: Uri): File {
        return filesHelper.createTempFile().also {
            filesHelper.saveUriToFile(uri, it)
        }
    }

    private fun copyBitmapToFile(bitmap: Bitmap): File =
        filesHelper.createTempFile().also {
            filesHelper.saveBitmapToFile(bitmap, it)
        }
}