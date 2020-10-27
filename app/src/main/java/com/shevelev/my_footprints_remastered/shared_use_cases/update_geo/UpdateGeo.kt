package com.shevelev.my_footprints_remastered.shared_use_cases.update_geo

import com.shevelev.my_footprints_remastered.common_entities.Footprint

interface UpdateGeo {
    /**
     * Can we load a geographic data?
     * @param singleFootprintRun The value is true if we run a code for a single footprint (not in a bulk mode inside WorkManager)
     * @return The value is true if we can load data
     */
    fun canLoad(singleFootprintRun: Boolean): Boolean

    /**
     * @return The value is true in case of success
     */
    fun update(footprint: Footprint): Boolean
}