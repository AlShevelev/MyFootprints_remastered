package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater

import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Provides hot data update for TitleFragment
 */
@FlowPreview
@ExperimentalCoroutinesApi
@ActivityScope
class TitleDataUpdaterImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val footprintRepository: FootprintRepository
) : TitleDataUpdaterConsumer,
    TitleDataUpdaterProvider {

    private val totalFootprintsChannel: ConflatedBroadcastChannel<Int> = ConflatedBroadcastChannel(0)
    override val totalFootprints: Flow<Int>
        get() = totalFootprintsChannel.asFlow()


    private val lastFootprintUriChannel: ConflatedBroadcastChannel<String?> = ConflatedBroadcastChannel(null)
    override val lastFootprintFileName: Flow<String?>
        get() = lastFootprintUriChannel.asFlow()

    override suspend fun init() {
        withContext(dispatchersProvider.ioDispatcher) {
            updateTotalFootprints(footprintRepository.getCount())
            updateLastFootprintUri(footprintRepository.getLast()?.imageContentUri)
        }
    }

    override suspend fun updateTotalFootprints(total: Int) = totalFootprintsChannel.send(total)

    override suspend fun updateLastFootprintUri(last: String?) = lastFootprintUriChannel.send(last)
}