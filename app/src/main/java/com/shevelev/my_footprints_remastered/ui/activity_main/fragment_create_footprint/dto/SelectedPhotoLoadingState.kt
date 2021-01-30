package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto

import androidx.annotation.StringRes
import java.io.File

sealed class SelectedPhotoLoadingState {
    object Loading : SelectedPhotoLoadingState()

    data class Ready(
        val file: File
    ) : SelectedPhotoLoadingState()

    data class Error(
        @StringRes
        val errorTextResId: Int
    ) : SelectedPhotoLoadingState()
}