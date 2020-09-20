package com.shevelev.my_footprints_remastered.storages.db.repositories

import com.shevelev.my_footprints_remastered.common_entities.Footprint

interface FootprintRepository {
    fun getCount(): Int

    fun getLast(): Footprint?

    fun create(footprint: Footprint)
}