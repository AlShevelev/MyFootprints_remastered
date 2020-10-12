package com.shevelev.my_footprints_remastered.shared_use_cases

import android.location.Location
import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.common_entities.UpdateFootprintInfo
import com.shevelev.my_footprints_remastered.entities.FootprintUpdateInfo
import java.io.File

interface CreateEditFootprint {
    suspend fun create(info: CreateFootprintInfo): FootprintUpdateInfo

    /**
     * @return null - nothing to update
     */
    suspend fun update(info: UpdateFootprintInfo): FootprintUpdateInfo?

    suspend fun clearDraft(draftImageFile: File?)
}