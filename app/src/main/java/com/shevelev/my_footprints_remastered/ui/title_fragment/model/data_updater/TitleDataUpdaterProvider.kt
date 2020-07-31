package com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater

interface TitleDataUpdaterProvider {
    suspend fun updateTotalFootprints(total: Int)

    suspend fun updateLastFootprintFileName(last: String?)
}