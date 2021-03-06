package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view.pager

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintListItem
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_page.GalleryPageFragment
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedDiffAlg
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem

class GalleryPagesAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    private var items = listOf<VersionedListItem>()

    fun updateItems(newItems: List<VersionedListItem>) {
        val diffCallback = VersionedDiffAlg(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun createFragment(position: Int): Fragment =
        (items[position] as FootprintListItem).let { footprintItem ->
            GalleryPageFragment.newInstance(footprintItem.imageFile, footprintItem.useCacheForImage)
        }

    override fun getItemId(position: Int): Long = if (position < 0) RecyclerView.NO_ID else items[position].id

    override fun containsItem(itemId: Long): Boolean = items.any { it.id == itemId }

    override fun getItemCount(): Int = items.size
}