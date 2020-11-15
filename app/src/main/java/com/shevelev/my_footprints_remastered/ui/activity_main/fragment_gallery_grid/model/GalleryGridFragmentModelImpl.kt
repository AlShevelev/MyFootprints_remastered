package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowConsumer
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
    override val updateFootprintData: UpdateFootprintDataFlowConsumer,
    override val deleteFootprintData: DeleteFootprintDataFlowConsumer,
    private val filesHelper: FilesHelper
) : GalleryGridFragmentModel,
    ModelBaseImpl() {

    private var _items: MutableList<Footprint> = mutableListOf()
    override val items: List<Footprint>
        get() = _items

    override suspend fun loadItems(): List<VersionedListItem> =
        withContext(dispatchersProvider.ioDispatcher) {
            footprintRepository.getAll()
                .let { dbItems ->
                    _items = dbItems.toMutableList()
                    dbItems.map { it.mapToListItem() }
                }
        }

    override suspend fun updateFootprint(updatedFootprint: Footprint): List<VersionedListItem>? =
        withContext(dispatchersProvider.calculationsDispatcher) {
            items.indexOfFirst { it.id == updatedFootprint.id }
                .takeIf { it != -1 }
                ?.let { index ->
                    _items[index] = updatedFootprint

                    val result = _items.map { it.mapToListItem() }.toMutableList()

                    val updatedItem = result[index]
                    result[index] = updatedItem.copy(version = updatedItem.version + 1, useCacheForImage = false)

                    result
                }
        }

    override suspend fun deleteFootprint(footprintId: Long): List<VersionedListItem> =
        withContext(dispatchersProvider.calculationsDispatcher) {
            val index = _items.indexOfFirst { it.id == footprintId }
            _items.removeAt(index)
            _items.map { it.mapToListItem() }.toMutableList()
        }

    private fun Footprint.mapToListItem() = FootprintListItem.create(this, filesHelper)
}