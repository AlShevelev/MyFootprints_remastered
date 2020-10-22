package com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last

import com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow.FragmentsDataFlowConsumer

interface LastFootprintDataFlowConsumer: FragmentsDataFlowConsumer<LastFootprintFlowInfo> {
    suspend fun init()
}