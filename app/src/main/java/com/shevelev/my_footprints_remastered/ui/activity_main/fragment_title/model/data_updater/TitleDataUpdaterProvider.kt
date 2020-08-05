package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater

interface TitleDataUpdaterProvider {
    suspend fun updateTotalFootprints(total: Int)

    suspend fun updateLastFootprintFileName(last: String?)
}