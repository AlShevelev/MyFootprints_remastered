package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater

import kotlinx.coroutines.flow.Flow

interface TitleDataUpdaterConsumer {
    val totalFootprints: Flow<Int>
    val lastFootprintFileName: Flow<String?>

    suspend fun init()
}