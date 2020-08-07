package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import java.io.File

sealed class PhotoContainerState {
    object Initial : PhotoContainerState()

    /**
     * An image is loading for a first time
     */
    object Loading : PhotoContainerState()

    /**
     * An image is ready to set
     */
    data class Ready(val image: File) : PhotoContainerState()
}

