package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.pins_cache

import android.util.LruCache
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDraw
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PinsCacheImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val pinDraw: PinDraw
) : PinsCache {

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

    override suspend fun get(footprint: FootprintOnMap): PinDrawInfo =
        cache.get(footprint.id)
            ?: run {
                withContext(dispatchersProvider.calculationsDispatcher) {
                    pinDraw.draw(
                        footprint.pinColor.backgroundColor,
                        footprint.pinColor.textColor,
                        footprint.imageContentUri,
                        footprint.comment)
                }
                .also {
                    cache.put(footprint.id, it)
                }
            }
}