package com.shevelev.my_footprints_remastered.ui.title_fragment.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater.TitleDataUpdaterConsumer

interface TitleFragmentModel : ModelBase {
    val titleData: TitleDataUpdaterConsumer
}