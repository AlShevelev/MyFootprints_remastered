package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import org.threeten.bp.ZonedDateTime
import java.io.File

data class FootprintListItem(
    override val id: Long,
    override val version: Long,

    override val isFirstItem: Boolean,
    override val isLastItem: Boolean,

    val useCacheForImage: Boolean,
    val imageFile: File,

    val created: ZonedDateTime,
    val city: String?,
    val country: String?
) : VersionedListItem {

    companion object {
        fun create(footprint: Footprint, filesHelper: FilesHelper) =
            FootprintListItem(
                id = footprint.id,
                version = 0,
                isFirstItem = false,
                isLastItem = false,
                useCacheForImage = true,
                imageFile = filesHelper.createImageFile(footprint.imageFileName),
                created = footprint.created,
                city = footprint.city,
                country = footprint.country
            )
    }
}