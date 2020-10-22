package com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete

import com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow.FragmentsDataFlowImpl
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@ActivityScope
class DeleteFootprintDataFlowImpl
@Inject
constructor(
) : FragmentsDataFlowImpl<DeleteFootprintFlowInfo>(),
    DeleteFootprintDataFlowConsumer,
    DeleteFootprintDataFlowProvider