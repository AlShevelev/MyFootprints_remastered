package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import javax.inject.Inject

class TitleFragmentModelImpl
@Inject
constructor(
    override val lastFootprintData: LastFootprintDataFlowConsumer,
    override val updateFootprintData: UpdateFootprintDataFlowConsumer
) : ModelBaseImpl(),
    TitleFragmentModel {

    override var lastFootprintId: Long? = null

}