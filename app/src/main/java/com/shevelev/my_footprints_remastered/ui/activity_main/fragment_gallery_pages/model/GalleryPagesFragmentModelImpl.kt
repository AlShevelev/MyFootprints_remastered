package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
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
    override var currentIndex: Int
) : ModelBaseImpl(),
    GalleryPagesFragmentModel {

    private lateinit var items: List<VersionedListItem>

    override suspend fun loadItems(): List<VersionedListItem> {
        items = withContext(dispatchersProvider.calculationsDispatcher) {
            footprints.map { FootprintListItem(
                id = it.id,
                version = 0,
                isFirstItem = false,
                isLastItem = false,
                footprint = it
            ) }
        }

        return items
    }

    override fun getFootprint(index: Int): Footprint = (items[index] as FootprintListItem).footprint
}