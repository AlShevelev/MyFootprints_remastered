package com.shevelev.my_footprints_remastered.storages.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shevelev.my_footprints_remastered.storages.db.entities.FootprintDb

@Dao
interface FootprintDao {
    @Insert
    fun create(footprint: FootprintDb)

    @Query("select count(1) from footprint")
    fun readCount(): Int

    @Query("select * from footprint order by created_sort desc limit 1")
    fun readLast(): FootprintDb?

    @Query("select * from footprint order by created_sort desc")
    fun readAll(): List<FootprintDb>

    @Query("select * from footprint where is_geo_loaded = false")
    fun readAllWithoutGeo(): List<FootprintDb>

    @Update
    fun update(footprint: FootprintDb)

    @Query("update footprint set city = :city, country = :country where footprint_id = :id")
    fun updateGeo(id: Long, city: String?, country: String?)

    @Query("delete from footprint where footprint_id = :id")
    fun delete(id: Long)
}