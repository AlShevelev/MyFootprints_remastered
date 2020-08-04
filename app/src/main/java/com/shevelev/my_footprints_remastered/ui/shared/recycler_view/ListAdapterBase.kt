package com.shevelev.my_footprints_remastered.ui.shared.recycler_view

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * Updatable list adapter
 */
abstract class ListAdapterBase<TEventsProcessor: ListItemEventsProcessor, TItem: ListItem>(
    private val listItemEventsProcessor: TEventsProcessor,
    private val pageSize: Int?
) : RecyclerView.Adapter<ViewHolderBase<TEventsProcessor, TItem>>(),
    AdapterRawDataAccess<TItem> {

    protected var items: List<TItem> = listOf()

    open fun update(newItems: List<TItem>) {
        val diffCallback = createDiffAlg(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems.toList()
        try{
            diffResult.dispatchUpdatesTo(this)
        } catch (e: Exception){
            Timber.e(e)
        }
    }

    override fun getItemId(position: Int): Long = items[getItemPosition(position)].id

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolderBase<TEventsProcessor, TItem>, position: Int) {
        holder.init(items[getItemPosition(position)], listItemEventsProcessor)
        checkNextPageReached(pageSize, items.size, position)
    }

    override fun onViewRecycled(holder: ViewHolderBase<TEventsProcessor, TItem>) =  holder.release()

    override fun getItem(position: Int): TItem = items[position]

    protected open fun getItemPosition(sourcePosition: Int): Int = sourcePosition

    protected abstract fun createDiffAlg(oldData: List<TItem>, newData: List<TItem>): DiffAlgBase<TItem>

    protected open fun checkNextPageReached(pageSize: Int?, itemsSize: Int, position: Int) {
        pageSize?.let {
            if (position > itemsSize - it / 2) {
                onNextPageReached()
            }
        }
    }

    protected open fun onNextPageReached() {}
}