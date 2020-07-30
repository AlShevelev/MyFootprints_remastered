package com.shevelev.my_footprints_remastered.storages.db.builder

import android.content.Context
import androidx.room.Room
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.storages.db.core.DbCoreImpl

object DatabaseBuilder {
    fun build(appContext: Context): DbCore =
        Room
        .databaseBuilder(appContext, DbCoreImpl::class.java, "my_footprints.db")
        .build()
}