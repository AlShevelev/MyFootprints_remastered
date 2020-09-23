package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater

import android.net.Uri

interface TitleDataUpdaterProvider {
    suspend fun updateTotalFootprints(total: Int)

    suspend fun updateLastFootprintUri(last: Uri?)
}