package com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint

import android.content.Context
import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.UpdateFootprintInfo
import com.shevelev.my_footprints_remastered.services.update_geo_service.UpdateGeoService
import com.shevelev.my_footprints_remastered.shared_use_cases.update_geo.UpdateGeo
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import dagger.Lazy
import org.threeten.bp.ZonedDateTime
import java.io.File
import javax.inject.Inject

class CreateUpdateFootprintImpl
@Inject
constructor(
    private val appContext: Context,
    private val filesHelper: FilesHelper,
    private val footprintRepository: FootprintRepository,
    private val keyValueStorageFacade: KeyValueStorageFacade,
    private val updateGeoUseCase: Lazy<UpdateGeo>
) : CreateUpdateFootprint {

    override suspend fun create(info: CreateFootprintInfo): FootprintCreateInfo {

        // Store the image
        val imageFileName = storeImage(info.draftImageFile)

        // Put a footprint into Db
        val footprint = Footprint(
            id = IdUtil.generateLongId(),
            imageFileName = imageFileName,
            location = info.location,
            comment = info.comment,
            pinColor = info.pinColor,
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

        // Update geo data if needed
        if(updateGeoUseCase.get().canLoad(true)) {
            UpdateGeoService.start(appContext, footprint)
        }

        return FootprintCreateInfo(footprint.id, imageFileName, footprintRepository.getCount())
    }

    /**
     * @return null - nothing to update
     */
    override suspend fun update(info: UpdateFootprintInfo): FootprintUpdateInfo? {
        val result = if(isNeedToUpdate(info)) {

            // update the image
            updateImage(info.draftImageFile, info.oldFootprint.imageFileName)

            val locationUpdated = info.location != info.oldFootprint.location

            // Update a footprint in Db
            val footprint = Footprint(
                id = info.oldFootprint.id,
                imageFileName = info.oldFootprint.imageFileName,
                location = info.location,
                comment = info.comment,
                pinColor = info.pinColor,
                created = info.oldFootprint.created,
                city = if(locationUpdated) null else info.oldFootprint.city,
                country = if(locationUpdated) null else info.oldFootprint.country,
                isGeoLoaded = !locationUpdated

            )
            footprintRepository.update(footprint)

            // Store last used pin color
            keyValueStorageFacade.savePinColor(info.pinColor)

            // Update geo data if needed
            if(locationUpdated && updateGeoUseCase.get().canLoad(true)) {
                UpdateGeoService.start(appContext, footprint)
            }

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
        deleteImage(footprint.imageFileName)

        // Remove from Db
        footprintRepository.delete(footprint.id)

        val last = footprintRepository.getLast()
        val count = footprintRepository.getCount()

        return FootprintDeleteInfo(last?.id, last?.imageFileName, count)
    }

    override suspend fun clearDraft(draftImageFile: File?) {
        draftImageFile?.let { filesHelper.deleteFile(it) }
    }

    private fun storeImage(draftImageFile: File): String {
        val imageFile = filesHelper.createImageFile()
        filesHelper.copyFile(draftImageFile, imageFile)

        return imageFile.name
    }

    private fun updateImage(draftImageFile: File, oldImageFileName: String): String {
        val oldImageFile = filesHelper.createImageFile(oldImageFileName)

        filesHelper.copyFile(draftImageFile, oldImageFile)

        return oldImageFile.name
    }

    private fun deleteImage(imageFileName: String) {
        val imageFile = filesHelper.createImageFile(imageFileName)
        imageFile.delete()
    }

    private fun isNeedToUpdate(info: UpdateFootprintInfo): Boolean {
        return info.isImageUpdated
                || info.comment != info.oldFootprint.comment
                || info.location != info.oldFootprint.location
                || info.pinColor != info.oldFootprint.pinColor
    }
}