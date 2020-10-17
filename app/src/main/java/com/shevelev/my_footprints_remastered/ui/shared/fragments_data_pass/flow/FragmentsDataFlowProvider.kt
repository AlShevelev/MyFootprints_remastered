package com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow

interface FragmentsDataFlowProvider<T> {
    suspend fun update(updatedData: T)
}