package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid

import android.view.ViewGroup
import coil.api.load
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
        imageDispose = itemView.photoImage.load(listItem.footprint.imageContentUri)

        itemView.footprintDateText.text = listItem.footprint.created.toShortDisplayString()
    }

    override fun release() {
        itemView.setOnClickListener(null)
        imageDispose?.takeIf { !it.isDisposed } ?.dispose()
    }
}