package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

interface GalleryGridFragmentModel : ModelBase {
    val items: List<Footprint>

    val updateFootprintData: UpdateFootprintDataFlowConsumer

    val deleteFootprintData: DeleteFootprintDataFlowConsumer

    suspend fun loadItems(): List<VersionedListItem>

    suspend fun updateFootprint(updatedFootprint: Footprint): List<VersionedListItem>?

    suspend fun deleteFootprint(footprintId: Long): List<VersionedListItem>
}