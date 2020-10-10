package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.shevelev.my_footprints_remastered.common_entities.PinColor

data class FootprintOnMap(
    val id: Long,
    val imageContentUri: Uri,
    private val location: LatLng,
    private val comment: String?,
    val pinColor: PinColor
): ClusterItem {
    /**
     * The position of this marker. This must always return the same value.
     */
    override fun getPosition(): LatLng = location

    /**
     * The title of this marker.
     */
    override fun getTitle(): String? = comment

    /**
     * The description of this marker.
     */
    override fun getSnippet(): String? = null
}