package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model

import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer

interface TitleFragmentModel : ModelBase {
    val lastFootprintData: LastFootprintDataFlowConsumer

    val updateFootprintData: UpdateFootprintDataFlowConsumer

    var lastFootprintId: Long?
}