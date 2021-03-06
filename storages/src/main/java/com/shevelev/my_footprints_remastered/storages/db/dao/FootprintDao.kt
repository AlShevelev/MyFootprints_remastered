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

    @Query("select * from footprint where is_geo_loaded = 0")
    fun readAllWithoutGeo(): List<FootprintDb>

    @Query("select * from footprint where footprint_id = :footprintId")
    fun readById(footprintId: Long): FootprintDb?

    @Update
    fun update(footprint: FootprintDb)

    @Query("update footprint set city = :city, country = :country where footprint_id = :id")
    fun updateGeo(id: Long, city: String?, country: String?)

    @Query("update footprint set gd_file_id = :gdFileId where footprint_id = :id")
    fun updateGoogleDriveFileId(id: Long, gdFileId: String)

    @Query("delete from footprint where footprint_id = :id")
    fun delete(id: Long)
}