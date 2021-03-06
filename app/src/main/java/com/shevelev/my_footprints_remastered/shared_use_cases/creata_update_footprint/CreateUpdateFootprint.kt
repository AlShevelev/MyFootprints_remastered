package com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint

import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.UpdateFootprintInfo
import java.io.File

interface CreateUpdateFootprint {
    suspend fun create(info: CreateFootprintInfo): FootprintCreateInfo

    /**
     * @return null - nothing to update
     */
    suspend fun update(info: UpdateFootprintInfo): FootprintUpdateInfo?

    suspend fun delete(footprint: Footprint): FootprintDeleteInfo

    suspend fun clearDraft(draftImageFile: File?)
}