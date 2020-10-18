package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
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
    private val footprints: List<Footprint>,
    override var currentIndex: Int,
    override val updateFootprintData: UpdateFootprintDataFlowConsumer
) : ModelBaseImpl(),
    GalleryPagesFragmentModel {

    private lateinit var items: MutableList<FootprintListItem>

    override suspend fun loadItems(): List<VersionedListItem> {
        items = withContext(dispatchersProvider.calculationsDispatcher) {
            footprints.map { FootprintListItem(
                id = it.id,
                version = 0,
                isFirstItem = false,
                isLastItem = false,
                footprint = it,
                useCacheForImage = true
            )}.toMutableList()
        }

        return items
    }

    override suspend fun updateFootprint(updatedFootprint: Footprint): List<VersionedListItem>? =
        withContext(dispatchersProvider.calculationsDispatcher) {
            items.indexOfFirst { it.footprint.id == updatedFootprint.id }
                .takeIf { it != -1 }
                ?.let { index ->
                    val item = items[index]
                    items[index] = item.copy(
                        footprint = updatedFootprint,
                        version = item.version+1,
                        useCacheForImage = false)
                    items
                }
        }

    override fun getFootprint(index: Int): Footprint = items[index].footprint
}