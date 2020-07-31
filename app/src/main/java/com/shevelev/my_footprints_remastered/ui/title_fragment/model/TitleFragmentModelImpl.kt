package com.shevelev.my_footprints_remastered.ui.title_fragment.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.title_fragment.model.data_updater.TitleDataUpdaterConsumer
import javax.inject.Inject

class TitleFragmentModelImpl
@Inject
constructor(
    override val titleData: TitleDataUpdaterConsumer
) : ModelBaseImpl(), TitleFragmentModel {
}