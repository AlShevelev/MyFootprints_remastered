package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model

import android.content.Context
import android.net.Uri
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto.SelectedPhotoLoadingState
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
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
    private val dataBridge: CreateFootprintFragmentDataBridge
) : ModelBaseImpl(),
    CreateFootprintFragmentModel {

    private var photoImage: File? = null

    override suspend fun checkNewPhotoSelected(callbackAction: (SelectedPhotoLoadingState) -> Unit) {
        val selectedPhotoFile = dataBridge.extractSelectedPhotoFile()
        val selectedPhotoUri = dataBridge.extractSelectedPhotoUri()

        when {
            selectedPhotoFile != null && selectedPhotoUri == null -> {
                photoImage = selectedPhotoFile
                callbackAction(SelectedPhotoLoadingState.Ready(photoImage!!))
            }
            selectedPhotoFile == null && selectedPhotoUri != null -> {
                if(photoImage == null) {
                    callbackAction(SelectedPhotoLoadingState.Loading)
                } else {
                    callbackAction(SelectedPhotoLoadingState.Updating)
                }

                withContext(dispatchersProvider.ioDispatcher) {
                    photoImage = copyUriToFile(selectedPhotoUri)
                }

                callbackAction(SelectedPhotoLoadingState.Ready(photoImage!!))
            }
        }
    }

    private fun copyUriToFile(uri: Uri): File {
        val targetFile = File.createTempFile("tmp_", "${IdUtil.generateLongId()}.jpg", appContext.cacheDir)

        appContext.contentResolver.openInputStream(uri).use { input ->
            targetFile.outputStream().use { fileOut ->
                input?.copyTo(fileOut)
            }
        }

        return targetFile
    }
}