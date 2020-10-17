package com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow

import kotlinx.coroutines.flow.Flow

interface FragmentsDataFlowConsumer<T> {
    val data: Flow<T?>
}