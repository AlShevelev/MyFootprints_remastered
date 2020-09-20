package com.shevelev.my_footprints_remastered.shared_use_cases

import android.location.Location
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import org.threeten.bp.ZonedDateTime
import java.io.File
import javax.inject.Inject

class CreateEditFootprintImpl
@Inject
constructor(
    private val filesHelper: FilesHelper,
    private val footprintRepository: FootprintRepository,
    private val keyValueStorageFacade: KeyValueStorageFacade
) : CreateEditFootprint {

    override fun create(draftFile: File, location: Location, comment: String?, pinColor: PinColor) {
        // Put an image to a final file
        val finalFile = filesHelper.createFile()
        filesHelper.copyFile(draftFile, finalFile)

        // Put a footprint into Db
        val footprint = Footprint(
            id = IdUtil.generateLongId(),
            fileName = finalFile.name,
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
        filesHelper.deleteFile(draftFile)
    }
}