package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint

import android.graphics.Color
import android.location.Location
import androidx.annotation.ColorInt
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import java.io.File
import javax.inject.Inject

class SharedFootprint
@Inject
constructor() {
    var comment: String? = null
    var image: File? = null

    var pinColor: PinColor = PinColor(Color.WHITE, Color.RED)

    var manualSelectedLocation: Location? = null
}