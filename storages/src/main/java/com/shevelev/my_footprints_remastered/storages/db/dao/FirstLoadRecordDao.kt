package com.shevelev.my_footprints_remastered.storages.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.shevelev.my_footprints_remastered.storages.db.entities.FirstLoadRecordDb

@Dao
interface FirstLoadRecordDao {
    @Insert
    @Transaction
    fun create(records: List<FirstLoadRecordDb>)

    @Query("select * from first_load_record")
    fun readAll(): List<FirstLoadRecordDb>

    @Query("delete from first_load_record where gd_file_id = :gdFileId")
    fun delete(gdFileId: String)
}