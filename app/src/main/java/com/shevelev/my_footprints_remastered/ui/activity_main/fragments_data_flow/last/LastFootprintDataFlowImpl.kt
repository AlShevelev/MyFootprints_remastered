package com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last

import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.ui.shared.fragments_data_pass.flow.FragmentsDataFlowImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Provides hot data update for TitleFragment
 */
@FlowPreview
@ExperimentalCoroutinesApi
@ActivityScope
class LastFootprintDataFlowImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val footprintRepository: FootprintRepository
) : FragmentsDataFlowImpl<LastFootprintInfo>(),
    LastFootprintDataFlowConsumer,
    LastFootprintDataFlowProvider {

    override suspend fun init() {
        withContext(dispatchersProvider.ioDispatcher) {
            val lastFootprint = footprintRepository.getLast()

            val lastInfo = LastFootprintInfo(
                totalFootprints = footprintRepository.getCount(),
                lastFootprintUri = lastFootprint?.imageContentUri,
                lastFootprintId = lastFootprint?.id
            )

            update(lastInfo)
        }
    }
}