package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.model

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.dto.Event
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase

interface LoadingProgressFragmentModel : ModelBase {
    fun setEventsListener(listener: ((Event) -> Unit)?)

    suspend fun tryToStartService()
}