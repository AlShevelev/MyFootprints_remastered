package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.model

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryGridFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val footprintRepository: FootprintRepository
) : GalleryGridFragmentModel,
    ModelBaseImpl() {

    private lateinit var items: List<Footprint>

    override suspend fun getItems(): List<VersionedListItem> {
        items = withContext(dispatchersProvider.ioDispatcher) {
            footprintRepository.getAll()
        }

        return items.map { FootprintListItem(
            id = it.id,
            version = 0,
            isFirstItem = false,
            isLastItem = false,
            footprint = it
        )}
    }
}