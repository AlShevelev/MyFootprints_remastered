package com.shevelev.my_footprints_remastered.common_entities.sync

/**
 * One record in synchronization log
 */
data class SyncRecord (
    val id: Long,
    val footprintId: Long,
    val operation: SyncOperation
)