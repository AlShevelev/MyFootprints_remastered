package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater.TitleDataUpdaterConsumer

interface TitleFragmentModel : ModelBase {
    val titleData: TitleDataUpdaterConsumer
}