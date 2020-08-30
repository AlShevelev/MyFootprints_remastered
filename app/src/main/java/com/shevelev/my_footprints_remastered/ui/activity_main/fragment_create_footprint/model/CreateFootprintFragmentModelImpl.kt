package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CreateFootprintFragmentModelImpl
@Inject
constructor(
    private val  appContext: Context,
    private val dispatchersProvider: DispatchersProvider,
    private val dataBridge: CreateFootprintFragmentDataBridge,
    override val geolocationProvider: GeolocationProviderManager,
    override val sharedFootprint: SharedFootprint
) : ModelBaseImpl(),
    CreateFootprintFragmentModel {

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

    private fun copyUriToFile(uri: Uri): File {
        val targetFile = createPhotoFile()

        appContext.contentResolver.openInputStream(uri).use { input ->
            targetFile.outputStream().use { fileOut ->
                input?.copyTo(fileOut)
            }
        }

        return targetFile
    }

    private fun copyBitmapToFile(bitmap: Bitmap): File {
        val targetFile = createPhotoFile()

        targetFile.outputStream().use { fileOut ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOut)
        }

        return targetFile
    }

    private fun createPhotoFile(): File = File.createTempFile("tmp_", "${IdUtil.generateLongId()}.jpg", appContext.cacheDir)
}