package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryGridFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val footprintRepository: FootprintRepository,
    override val updateFootprintData: UpdateFootprintDataFlowConsumer
) : GalleryGridFragmentModel,
    ModelBaseImpl() {

    override val items: List<Footprint>
        get() = gridItems.map { it.footprint }

    private lateinit var gridItems: MutableList<FootprintListItem>

    override suspend fun loadItems(): List<VersionedListItem> =
        withContext(dispatchersProvider.ioDispatcher) { footprintRepository.getAll()}
            .let { dbItems ->
                dbItems.map { FootprintListItem(
                    id = it.id,
                    version = 0,
                    isFirstItem = false,
                    isLastItem = false,
                    footprint = it,
                    useCacheForImage = true
                )}
            }
            .also {
                gridItems = it.toMutableList()
            }

    override suspend fun updateFootprint(updatedFootprint: Footprint): List<VersionedListItem>? =
        withContext(dispatchersProvider.calculationsDispatcher) {
            gridItems.indexOfFirst { it.footprint.id == updatedFootprint.id }
                .takeIf { it != -1 }
                ?.let { index ->
                    val item = gridItems[index]
                    gridItems[index] = item.copy(
                        footprint = updatedFootprint,
                        version = item.version+1,
                        useCacheForImage = false
                    )
                    gridItems
                }
        }
}