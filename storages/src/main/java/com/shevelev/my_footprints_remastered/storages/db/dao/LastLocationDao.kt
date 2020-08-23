package com.shevelev.my_footprints_remastered.storages.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shevelev.my_footprints_remastered.storages.db.entities.LastLocationDb

@Dao
interface LastLocationDao {
    @Insert
    fun create(item: LastLocationDb)

    @Update
    fun update(item: LastLocationDb)

    @Query("select * from last_location limit 1")
    fun read(): LastLocationDb?
}