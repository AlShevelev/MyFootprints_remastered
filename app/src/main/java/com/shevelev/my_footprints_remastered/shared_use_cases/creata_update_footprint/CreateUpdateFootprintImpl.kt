package com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.VOLUME_EXTERNAL_PRIMARY
import androidx.annotation.RequiresApi
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.UpdateFootprintInfo
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.coroutines.suspendCancellableCoroutine
import org.threeten.bp.ZonedDateTime
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume

class CreateUpdateFootprintImpl
@Inject
constructor(
    private val appContext: Context,
    private val filesHelper: FilesHelper,
    private val footprintRepository: FootprintRepository,
    private val keyValueStorageFacade: KeyValueStorageFacade,
) : CreateUpdateFootprint {

    private val imageMimeType = "image/jpeg"

    override suspend fun create(info: CreateFootprintInfo): FootprintCreateInfo {

        // Store the image
        val imageInfo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            storeImageNewWay(info.draftImageFile, info.comment)
        } else {
            storeImageOldWay(info.draftImageFile)
        }

        // Put a footprint into Db
        val footprint = Footprint(
            id = IdUtil.generateLongId(),
            imageContentUri = imageInfo.first,
            imageFileName = imageInfo.second,
            latitude = info.location.latitude,
            longitude = info.location.longitude,
            comment = info.comment,
            pinTextColor = info.pinColor.textColor,
            pinBackgroundColor = info.pinColor.backgroundColor,
            created = ZonedDateTime.now(),
            city = null,
            country = null,
            isGeoLoaded = false
        )
        footprintRepository.create(footprint)

        // Store last used pin color
        keyValueStorageFacade.savePinColor(info.pinColor)

        // Remove the draft file
        filesHelper.deleteFile(info.draftImageFile)

        return FootprintCreateInfo(footprint.id, footprint.imageContentUri, footprintRepository.getCount())
    }

    /**
     * @return null - nothing to update
     */
    override suspend fun update(info: UpdateFootprintInfo): FootprintUpdateInfo? {
        val result = if(isNeedToUpdate(info)) {

            // update the image
            val imageInfo: Pair<Uri, String?>? = if(info.isImageUpdated) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    updateImageNewWay(info.draftImageFile, info.comment, info.oldFootprint.imageContentUri)
                } else {
                    info.oldFootprint.imageFileName?.let { updateImageOldWay(info.draftImageFile, it) }
                }
            } else {
                null
            }

            // Update a footprint in Db
            val footprint = Footprint(
                id = info.oldFootprint.id,
                imageContentUri = imageInfo?.first ?: info.oldFootprint.imageContentUri,
                imageFileName = imageInfo?.second ?: info.oldFootprint.imageFileName,
                latitude = info.location.latitude,
                longitude = info.location.longitude,
                comment = info.comment,
                pinTextColor = info.pinColor.textColor,
                pinBackgroundColor = info.pinColor.backgroundColor,
                created = info.oldFootprint.created,
                city = info.oldFootprint.city,
                country = info.oldFootprint.country,
                isGeoLoaded = info.oldFootprint.isGeoLoaded

            )
            footprintRepository.update(footprint)

            // Store last used pin color
            keyValueStorageFacade.savePinColor(info.pinColor)

            FootprintUpdateInfo(footprint)
        } else {
            null
        }

        // Remove the draft file
        filesHelper.deleteFile(info.draftImageFile)

        return result
    }

    override suspend fun delete(footprint: Footprint): FootprintDeleteInfo {
        // Remove the image
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deleteImageNewWay(footprint.imageContentUri)
        } else {
            footprint.imageFileName?.let { deleteImageOldWay(it) }
        }

        // Remove from Db
        footprintRepository.delete(footprint.id)

        val last = footprintRepository.getLast()
        val count = footprintRepository.getCount()

        return FootprintDeleteInfo(last?.id, last?.imageContentUri, count)
    }

    override suspend fun clearDraft(draftImageFile: File?) {
        draftImageFile?.let { filesHelper.deleteFile(it) }
    }

    @RequiresApi(29)
    /**
     * @return content Uri and name of file with an image (for an old API only (<29))
     */
    private fun storeImageNewWay(draftImageFile: File, comment: String?): Pair<Uri, String?> =
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

            return@with Pair(imageUri, null)
        }

    @RequiresApi(29)
    /**
     * @return content Uri and name of file with an image (for an old API only (<29))
     */
    private fun updateImageNewWay(draftImageFile: File, comment: String?, imageUri: Uri): Pair<Uri, String?> =
        with(appContext.contentResolver) {
            val imageDetails = ContentValues().also { details ->
                details.put(MediaStore.Images.Media.DISPLAY_NAME, comment)
                details.put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            update(imageUri, imageDetails, null, null)

            draftImageFile.inputStream().use { input ->
                openOutputStream(imageUri)!!.use { output ->
                    input.copyTo(output)
                }
            }

            imageDetails.clear()
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
            update(imageUri, imageDetails, null, null)

            return@with Pair(imageUri, null)
        }

    @RequiresApi(29)
    private fun deleteImageNewWay(imageUri: Uri) = appContext.contentResolver.delete(imageUri, null, null)

    private suspend fun storeImageOldWay(draftImageFile: File): Pair<Uri, String?> {
        val imageFile = filesHelper.createImageFile(appContext.getString(R.string.appName))
        filesHelper.copyFile(draftImageFile, imageFile)

        return Pair(scanFile(imageFile)!!, imageFile.name)
    }

    private suspend fun updateImageOldWay(draftImageFile: File, oldImageFileName: String): Pair<Uri, String?> {
        val oldImageFile = filesHelper.createImageFile(appContext.getString(R.string.appName), oldImageFileName)

        filesHelper.copyFile(draftImageFile, oldImageFile)

        return Pair(scanFile(oldImageFile)!!, oldImageFile.name)
    }

    private suspend fun deleteImageOldWay(imageFileName: String) {
        val imageFile = filesHelper.createImageFile(appContext.getString(R.string.appName), imageFileName)
        imageFile.delete()
        scanFile(imageFile)
    }

    private suspend fun scanFile(shot: File): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(appContext, arrayOf<String>(shot.absolutePath), arrayOf(imageMimeType)) { _, uri ->
                continuation.resume(uri)
            }
        }
    }

    private fun isNeedToUpdate(info: UpdateFootprintInfo): Boolean {
        return info.isImageUpdated || info.comment != info.oldFootprint.comment
                || info.location.latitude != info.oldFootprint.latitude || info.location.longitude != info.oldFootprint.longitude
                || info.pinColor.backgroundColor != info.oldFootprint.pinBackgroundColor
                || info.pinColor.textColor != info.oldFootprint.pinTextColor
    }
}