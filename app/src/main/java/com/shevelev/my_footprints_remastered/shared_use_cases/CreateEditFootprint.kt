package com.shevelev.my_footprints_remastered.shared_use_cases

import android.location.Location
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.entities.FootprintUpdateInfo
import java.io.File

interface CreateEditFootprint {
    suspend fun create(draftImageFile: File, location: Location, comment: String?, pinColor: PinColor): FootprintUpdateInfo

    suspend fun clearDraft(draftImageFile: File?)
}