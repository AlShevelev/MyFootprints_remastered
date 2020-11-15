package com.shevelev.my_footprints_remastered.ui.shared.pin_draw

import androidx.annotation.ColorInt
import java.io.File

interface PinDraw {
    fun draw(
        @ColorInt backgroundColor: Int,
        @ColorInt textColor: Int,
        image: File?,
        text: String?): PinDrawInfo
}