package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.storages.files.BitmapFilesHelper
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintFlowInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintFlowInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryPagesFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    footprints: List<Footprint>,
    override var currentIndex: Int,
    override val updateFootprintData: UpdateFootprintDataFlowConsumer,
    private val createUpdateFootprint: CreateUpdateFootprint,
    private val lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
    private val deleteFootprintDataFlowProvider: DeleteFootprintDataFlowProvider,
    private val filesHelper: BitmapFilesHelper
) : ModelBaseImpl(),
    GalleryPagesFragmentModel {

    private val footprintsMutable = footprints.toMutableList()

    override suspend fun loadItems(): List<VersionedListItem> =
        withContext(dispatchersProvider.calculationsDispatcher) {
            footprintsMutable.map { it.mapToListItem() }.toMutableList()
        }

    override suspend fun updateFootprint(updatedFootprint: Footprint): List<VersionedListItem>? =
        withContext(dispatchersProvider.calculationsDispatcher) {
            footprintsMutable.indexOfFirst { it.id == updatedFootprint.id }
                .takeIf { it != -1 }
                ?.let { index ->
                    footprintsMutable[index] = updatedFootprint

                    val result = footprintsMutable.map { it.mapToListItem() }.toMutableList()

                    val updatedItem = result[index]
                    result[index] = updatedItem.copy(version = updatedItem.version + 1, useCacheForImage = false)

                    result
                }
        }

    override fun getFootprint(index: Int): Footprint = footprintsMutable[index]

    override suspend fun deleteFootprint(): List<VersionedListItem> {
        // Get footprint to delete
        val footprintToDelete = footprintsMutable[currentIndex]

        // Delete it
        val deleteResult = withContext(dispatchersProvider.ioDispatcher) {
            createUpdateFootprint.delete(footprintToDelete)
        }

        // Update data on the Title screen
        lastFootprintDataFlowProvider.update(LastFootprintFlowInfo(
            totalFootprints = deleteResult.totalFootprints,
            lastFootprintId = deleteResult.lastFootprintId,
            lastFootprintFileName = deleteResult.lastFootprintImageFileName
        ))

        return if(deleteResult.totalFootprints == 0) {
            listOf()
        } else {
            deleteFootprintDataFlowProvider.update(DeleteFootprintFlowInfo(footprintToDelete.id))

            val result = footprintsMutable
                .indexOfFirst { it.id == footprintToDelete.id }
                .let { index ->
                    footprintsMutable.removeAt(index)
                    footprintsMutable.map { it.mapToListItem() }
                }

            if(currentIndex > footprintsMutable.lastIndex) {
                currentIndex = footprintsMutable.lastIndex
            }

            result
        }
    }

    private fun Footprint.mapToListItem() = FootprintListItem.create(this, filesHelper)
}