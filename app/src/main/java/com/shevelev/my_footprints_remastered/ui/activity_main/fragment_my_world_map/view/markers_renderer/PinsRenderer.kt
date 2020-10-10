package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.markers_renderer

import android.content.Context
import android.util.LruCache
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo

class PinsRenderer(
    context: Context,
    private val pinDraw: PinDraw,
    map: GoogleMap,
    clusterManager: ClusterManager<FootprintOnMap>
) : DefaultClusterRenderer<FootprintOnMap>(context, map, clusterManager) {

    private val cache: LruCache<Long, PinDrawInfo>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        val cacheSize = maxMemory / 4

        cache = object : LruCache<Long, PinDrawInfo>(cacheSize) {
            override fun sizeOf(key: Long, bitmap: PinDrawInfo): Int {
                return bitmap.bitmap.byteCount / 1024
            }
        }
    }

    override fun onBeforeClusterItemRendered(item: FootprintOnMap, markerOptions: MarkerOptions) {
        val pin = getItem(item)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pin.bitmap))
        markerOptions.anchor(pin.spearheadRelativeX, 1f)
    }

    override fun onClusterItemUpdated(item: FootprintOnMap, marker: Marker) {
        val pin = getItem(item)
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(pin.bitmap))
        marker.setAnchor(pin.spearheadRelativeX, 1f)
    }

    private fun getItem(footprint: FootprintOnMap): PinDrawInfo =
        cache.get(footprint.id)
            ?: run {
                pinDraw.draw(
                    footprint.pinColor.backgroundColor,
                    footprint.pinColor.textColor,
                    footprint.imageContentUri,
                    footprint.title
                )
                .also {
                    cache.put(footprint.id, it)
                }
            }
}