package com.shevelev.my_footprints_remastered.storages.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.shevelev.my_footprints_remastered.storages.db.entities.FootprintDb

@Dao
interface FootprintDao {
    @Query("select count(1) from footprint")
    fun readCount(): Int

    @Query("select * from footprint order by created_sort desc limit 1")
    fun readLast(): FootprintDb?
}