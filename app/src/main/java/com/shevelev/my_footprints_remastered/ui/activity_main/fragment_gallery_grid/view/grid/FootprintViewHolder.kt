package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model.FootprintListItemEventsProcessor
import com.shevelev.my_footprints_remastered.ui.shared.glide.GlideTarget
import com.shevelev.my_footprints_remastered.ui.shared.glide.clear
import com.shevelev.my_footprints_remastered.ui.shared.glide.load
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.ViewHolderBase
import com.shevelev.my_footprints_remastered.utils.format.toShortDisplayString

class FootprintViewHolder(
    parentView: ViewGroup
) : ViewHolderBase<FootprintListItemEventsProcessor, FootprintListItem>(
    parentView,
    R.layout.list_item_footprint
) {
    private var imageDispose: GlideTarget? = null

    override fun init(listItem: FootprintListItem, listItemEventsProcessor: FootprintListItemEventsProcessor) {
        itemView.setOnClickListener { listItemEventsProcessor.onFootprintClick(listItem.id) }
        imageDispose = itemView
            .findViewById<ImageView>(R.id.photoImage)
            .load(listItem.imageFile, skipMemoryCache = !listItem.useCacheForImage)

        itemView.findViewById<TextView>(R.id.footprintDateTextGeo).text = getFootprintText(listItem)
    }

    override fun release() {
        itemView.setOnClickListener(null)
        imageDispose?.clear(itemView.context)
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