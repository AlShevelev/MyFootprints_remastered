package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface MyWorldMapFragmentModel : ModelBase {
    suspend fun getFootprints(): FootprintsOnMap
}