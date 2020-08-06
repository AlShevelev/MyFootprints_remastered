package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.dto

import java.io.File

sealed class SelectedPhotoLoadingState {
    data class Ready(val file: File): SelectedPhotoLoadingState()
    object Loading: SelectedPhotoLoadingState()
    object Updating: SelectedPhotoLoadingState()
}