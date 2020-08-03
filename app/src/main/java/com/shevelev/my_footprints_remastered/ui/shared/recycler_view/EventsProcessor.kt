package com.shevelev.my_footprints_remastered.ui.shared.recycler_view

interface ListItemEventsProcessor

interface RetryListItemEventsProcessor: ListItemEventsProcessor {
    fun onRetryLoadPage()
}