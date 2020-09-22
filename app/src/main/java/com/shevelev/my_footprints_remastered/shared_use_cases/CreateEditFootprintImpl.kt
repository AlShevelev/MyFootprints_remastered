package com.shevelev.my_footprints_remastered.shared_use_cases

import android.content.ContentValues
import android.content.Context
import android.location.Location
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.VOLUME_EXTERNAL_PRIMARY
import androidx.annotation.RequiresApi
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.coroutines.suspendCancellableCoroutine
import org.threeten.bp.ZonedDateTime
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume

class CreateEditFootprintImpl
@Inject
constructor(
    private val appContext: Context,
    private val filesHelper: FilesHelper,
    private val footprintRepository: FootprintRepository,
    private val keyValueStorageFacade: KeyValueStorageFacade
) : CreateEditFootprint {

    private val imageMimeType = "image/jpeg"

    override suspend fun create(draftImageFile: File, location: Location, comment: String?, pinColor: PinColor) {
        // Store the image
        val imageUri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            storeImageNewWay(draftImageFile, comment)
        } else {
            storeImageOldWay(draftImageFile, comment)
        }

        // Put a footprint into Db
        val footprint = Footprint(
            id = IdUtil.generateLongId(),
            imageContentUri = imageUri.toString(),
            latitude = location.latitude,
            longitude = location.longitude,
            comment = comment,
            pinTextColor = pinColor.textColor,
            pinBackgroundColor = pinColor.backgroundColor,
            created = ZonedDateTime.now()
        )
        footprintRepository.create(footprint)

        // Store last used pin color
        keyValueStorageFacade.savePinColor(pinColor)

        // Remove the draft file
        filesHelper.deleteFile(draftImageFile)
    }

    @RequiresApi(29)
    private fun storeImageNewWay(draftImageFile: File, comment: String?): Uri =
        with(appContext.contentResolver) {
            val mediaCollection = MediaStore.Images.Media.getContentUri(VOLUME_EXTERNAL_PRIMARY)

            val imageDetails = ContentValues().also { details ->
                comment?.let { details.put(MediaStore.Images.Media.DISPLAY_NAME, it) }

                details.put(MediaStore.Images.Media.MIME_TYPE, imageMimeType)
                details.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/${appContext.getString(R.string.appName)}/")

                details.put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val imageUri = insert(mediaCollection, imageDetails)!!

            draftImageFile.inputStream().use { input ->
                openOutputStream(imageUri)!!.use { output ->
                    input.copyTo(output)
                }
            }

            imageDetails.clear()
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
            update(imageUri, imageDetails, null, null)

            return@with imageUri
        }

    private suspend fun storeImageOldWay(draftImageFile: File, comment: String?): Uri {
        val imageFile = filesHelper.createImageFile(appContext.getString(R.string.appName))
        filesHelper.copyFile(draftImageFile, imageFile)

        return scanNewFile(imageFile)!!
    }

    private suspend fun scanNewFile(shot: File): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(appContext, arrayOf<String>(shot.absolutePath), arrayOf(imageMimeType)) { _, uri ->
                continuation.resume(uri)
            }
        }
    }
}