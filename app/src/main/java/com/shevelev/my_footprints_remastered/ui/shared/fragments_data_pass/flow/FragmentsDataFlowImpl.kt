package com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow

import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
@ActivityScope
open class FragmentsDataFlowImpl<T> : FragmentsDataFlowConsumer<T>, FragmentsDataFlowProvider<T> {
    private val dataChannel: BroadcastChannel<T?> = BroadcastChannel(1)

    override val data: Flow<T?>
        get() = dataChannel.asFlow()

    override suspend fun update(updatedData: T) = dataChannel.send(updatedData)
}