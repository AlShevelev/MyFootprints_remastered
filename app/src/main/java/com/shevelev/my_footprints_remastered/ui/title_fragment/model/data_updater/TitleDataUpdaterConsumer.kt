package com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater

import kotlinx.coroutines.flow.Flow

interface TitleDataUpdaterConsumer {
    val totalFootprints: Flow<Int>
    val lastFootprintFileName: Flow<String?>

    suspend fun init()
}