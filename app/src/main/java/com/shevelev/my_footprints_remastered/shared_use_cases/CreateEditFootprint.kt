package com.shevelev.my_footprints_remastered.shared_use_cases

import android.location.Location
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import java.io.File

interface CreateEditFootprint {
    fun create(draftFile: File, location: Location, comment: String?, pinColor: PinColor)
}