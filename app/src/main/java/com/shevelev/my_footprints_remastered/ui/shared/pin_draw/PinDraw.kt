package com.shevelev.my_footprints_remastered.ui.shared.pin_draw

import android.net.Uri
import androidx.annotation.ColorInt
import java.io.File

interface PinDraw {
    fun draw(
        @ColorInt backgroundColor: Int,
        @ColorInt textColor: Int,
        image: File?,
        text: String?): PinDrawInfo

    fun draw(
        @ColorInt backgroundColor: Int,
        @ColorInt textColor: Int,
        image: Uri?,
        text: String?): PinDrawInfo
}