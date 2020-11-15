package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid

import android.view.ViewGroup
import coil.api.load
import coil.request.CachePolicy
import coil.request.RequestDisposable
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model.FootprintListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import com.shevelev.my_footprints_remastered.utils.format.toShortDisplayString
import kotlinx.android.synthetic.main.list_item_footprint.view.*

class FootprintViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<FootprintListItemEventsProcessor, FootprintListItem>(
    parentView,
    R.layout.list_item_footprint
) {
    private var imageDispose: RequestDisposable? = null

    override fun init(listItem: FootprintListItem, listItemEventsProcessor: FootprintListItemEventsProcessor) {
        itemView.setOnClickListener { listItemEventsProcessor.onFootprintClick(listItem.id) }
        imageDispose = itemView.photoImage.load(listItem.imageFile) {
            if(listItem.useCacheForImage) {
                memoryCachePolicy(CachePolicy.ENABLED)
            } else {
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        }

        itemView.footprintDateTextGeo.text = getFootprintText(listItem)
    }

    override fun release() {
        itemView.setOnClickListener(null)
        imageDispose?.takeIf { !it.isDisposed } ?.dispose()
    }

    private fun getFootprintText(footprint: FootprintListItem): String {
        val builder = StringBuffer()

        builder.append(footprint.created.toShortDisplayString())

        when {
            !footprint.city.isNullOrBlank() -> {
                builder.append("; ")
                builder.append(footprint.city)
            }

            !footprint.country.isNullOrBlank() -> {
                builder.append("; ")
                builder.append(footprint.country)
            }
        }

        return builder.toString()
    }
}