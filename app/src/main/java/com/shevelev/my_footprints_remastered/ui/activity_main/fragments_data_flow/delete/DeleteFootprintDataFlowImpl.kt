package com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete

import com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow.FragmentsDataFlowImpl
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import javax.inject.Inject

@ActivityScope
class DeleteFootprintDataFlowImpl
@Inject
constructor(
) : FragmentsDataFlowImpl<DeleteFootprintFlowInfo>(),
    DeleteFootprintDataFlowConsumer,
    DeleteFootprintDataFlowProvider