package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintFlowInfo
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class InsertSetLocationFragmentModel
@Inject
constructor(
    private val appContext: Context,
    protected val dispatchersProvider: DispatchersProvider,
    protected val sharedFootprint: SharedFootprint,
    protected val createUpdateFootprint: CreateUpdateFootprint,
    private val geolocationProvider: GeolocationProviderData,
    private val lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
    override val isInUpdateMode: Boolean
) : ModelBaseImpl(),
    SetLocationFragmentModel  {
    override val isGooglePlayServicesAvailable: Boolean
        get() = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appContext) == ConnectionResult.SUCCESS

    override val canSave: Boolean
        get() = sharedFootprint.image != null

    override suspend fun save() {
        val createInfo = withContext(dispatchersProvider.ioDispatcher) {
            createUpdateFootprint.create(
                CreateFootprintInfo(
                    draftImageFile = sharedFootprint.image!!,
                    location = sharedFootprint.manualSelectedLocation ?: geolocationProvider.lastLocation,
                    comment = sharedFootprint.comment,
                    pinColor = sharedFootprint.pinColor
                ))
        }

        lastFootprintDataFlowProvider.update(
            LastFootprintFlowInfo(
            lastFootprintId = createInfo.lastFootprintId,
            lastFootprintUri = createInfo.lastFootprintImage,
            totalFootprints = createInfo.totalFootprints
        ))
    }
}