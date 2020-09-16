package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint

import android.graphics.Color
import android.location.Location
import androidx.annotation.ColorInt
import java.io.File
import javax.inject.Inject

class SharedFootprint
@Inject
constructor() {
    var comment: String? = null
    var image: File? = null

    @ColorInt
    var pinTextColor: Int = Color.WHITE

    @ColorInt
    var pinBackgroundColor: Int = Color.RED

    var manualSelectedLocation: Location? = null
}