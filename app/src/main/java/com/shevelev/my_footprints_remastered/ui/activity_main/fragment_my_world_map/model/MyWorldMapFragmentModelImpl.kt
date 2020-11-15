package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.model

import com.google.android.gms.maps.model.LatLng
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.FootprintsOnMap
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.dto.MapZoomAndLocation
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.delete.DeleteFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowConsumer
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.location.toMapLocation
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyWorldMapFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val footprintRepository: FootprintRepository,
    override val updateFootprintData: UpdateFootprintDataFlowConsumer,
    override val deleteFootprintData: DeleteFootprintDataFlowConsumer,
    private val filesHelper: FilesHelper
) : ModelBaseImpl(),
    MyWorldMapFragmentModel {

    override lateinit var footprints: MutableList<Footprint>
        private set

    override suspend fun getFootprintsForMap(): FootprintsOnMap {
        if(!::footprints.isInitialized) {
            footprints = withContext(dispatchersProvider.ioDispatcher) {
                footprintRepository.getAll().toMutableList()
            }
        }

        val result = withContext(dispatchersProvider.calculationsDispatcher) {
            footprints.map { it.mapToFootprintOnMap() }
        }

        val centerLocation = result.firstOrNull()?.position ?: LatLng(0.0, 0.0)

        return FootprintsOnMap(result, MapZoomAndLocation(0f, centerLocation))
    }

    /**
     * Get footprint index by it's Id
     * @return index of null if an item is not found
     */
    override fun getIndexById(id: Long): Int? = footprints.indexOfFirst { it.id == id }.takeIf { it != -1 }

    override suspend fun updateFootprint(updatedFootprint: Footprint): List<FootprintOnMap>? =
        withContext(dispatchersProvider.calculationsDispatcher) {
            footprints.indexOfFirst { it.id == updatedFootprint.id }
                .takeIf { it != -1 }
                ?.let { index ->
                    footprints[index] = updatedFootprint
                    footprints.map { it.mapToFootprintOnMap() }
                }
        }

    override suspend fun deleteFootprint(footprintId: Long): List<FootprintOnMap> =
        withContext(dispatchersProvider.calculationsDispatcher) {
            footprints
                .indexOfFirst { it.id == footprintId }
                .let { index ->
                    footprints.removeAt(index)
                    footprints.map { it.mapToFootprintOnMap() }
                }
        }

    private fun Footprint.mapToFootprintOnMap(): FootprintOnMap =
        FootprintOnMap(
            id = id,
            imageFile = filesHelper.createImageFile(imageFileName),
            location = location.toMapLocation(),
            comment = comment,
            pinColor = pinColor
        )
}