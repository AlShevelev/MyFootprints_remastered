package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

interface GalleryPagesFragmentModel : ModelBase {
    var currentIndex: Int

    val updateFootprintData: UpdateFootprintDataFlowConsumer

    suspend fun loadItems(): List<VersionedListItem>

    suspend fun updateFootprint(updatedFootprint: Footprint): List<VersionedListItem>?

    fun getFootprint(index: Int): Footprint

    /**
     * @return updated list of items
     */
    suspend fun deleteFootprint(): List<VersionedListItem>
}