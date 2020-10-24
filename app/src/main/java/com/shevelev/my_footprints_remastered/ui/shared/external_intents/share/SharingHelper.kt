package com.shevelev.my_footprints_remastered.ui.shared.external_intents.share

import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.common_entities.Footprint

interface SharingHelper {
    fun share(footprint: Footprint, fragment: Fragment)
}