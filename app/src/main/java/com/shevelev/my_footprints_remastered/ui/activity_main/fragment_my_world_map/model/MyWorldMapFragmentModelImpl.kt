package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model

import com.google.android.gms.maps.model.LatLng
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyWorldMapFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val footprintRepository: FootprintRepository
) : ModelBaseImpl(),
    MyWorldMapFragmentModel {

    private lateinit var footprints: List<FootprintOnMap>

    override suspend fun getFootprints(): FootprintsOnMap {
        if(!::footprints.isInitialized) {
            footprints = withContext(dispatchersProvider.ioDispatcher) {
                footprintRepository.getAll().map {
                    FootprintOnMap(
                        id = it.id,
                        imageContentUri = it.imageContentUri,
                        location = LatLng(it.latitude, it.longitude),
                        comment = it.comment,
                        pinColor = PinColor(it.pinTextColor, it.pinBackgroundColor)
                    )
                }
            }
        }

        val centerLocation = footprints.firstOrNull()?.location ?: LatLng(0.0, 0.0)

        return FootprintsOnMap(footprints, 0f, centerLocation)
    }
}