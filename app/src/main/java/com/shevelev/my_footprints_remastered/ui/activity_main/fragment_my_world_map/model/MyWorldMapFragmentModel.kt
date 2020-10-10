package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface MyWorldMapFragmentModel : ModelBase {
    val footprints: List<Footprint>

    suspend fun getFootprintsForMap(): FootprintsOnMap

    /**
     * Get footprint index by it's Id
     * @return index of null if an item is not found
     */
    fun getIndexById(id: Long): Int?
}