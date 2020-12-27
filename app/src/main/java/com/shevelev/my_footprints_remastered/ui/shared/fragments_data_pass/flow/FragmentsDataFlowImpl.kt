package com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow

import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@ActivityScope
open class FragmentsDataFlowImpl<T> : FragmentsDataFlowConsumer<T>, FragmentsDataFlowProvider<T> {
    private val dataFlow: MutableStateFlow<T?> = MutableStateFlow(null)

    override val data: Flow<T?>
        get() = dataFlow.asStateFlow()

    override suspend fun update(updatedData: T) {
        dataFlow.value = updatedData
    }
}