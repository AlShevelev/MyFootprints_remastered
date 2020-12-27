package com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow.FragmentsDataFlowImpl
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import javax.inject.Inject

/**
 * Provides hot data update when a user updates a footprint
 */
@ActivityScope
class UpdateFootprintDataFlowImpl
@Inject
constructor(
) : FragmentsDataFlowImpl<Footprint>(),
    UpdateFootprintDataFlowConsumer,
    UpdateFootprintDataFlowProvider