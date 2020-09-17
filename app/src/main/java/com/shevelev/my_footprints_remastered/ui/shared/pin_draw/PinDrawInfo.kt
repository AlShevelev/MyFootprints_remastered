package com.shevelev.my_footprints_remastered.ui.shared.pin_draw

import android.graphics.Bitmap

data class PinDrawInfo(
    val bitmap: Bitmap,

    /**
     * Relative X coordinate of a pip spearhead
     */
    val spearheadRelativeX: Float
)