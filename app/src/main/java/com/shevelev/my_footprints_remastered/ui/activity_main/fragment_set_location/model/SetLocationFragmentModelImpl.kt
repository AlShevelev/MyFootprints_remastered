package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.shevelev.my_footprints_remastered.common_entities.CreateFootprintInfo
import com.shevelev.my_footprints_remastered.shared_use_cases.CreateEditFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.data_updater.TitleDataUpdaterProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetLocationFragmentModelImpl
@Inject
constructor(
    private val appContext: Context,
    private val dispatchersProvider: DispatchersProvider,
    private val sharedFootprint: SharedFootprint,
    private val createEditFootprint: CreateEditFootprint,
    private val geolocationProvider: GeolocationProviderData,
    private val titleDataUpdaterProvider: TitleDataUpdaterProvider
) : ModelBaseImpl(),
    SetLocationFragmentModel  {
    override val isGooglePlayServicesAvailable: Boolean
        get() = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appContext) == ConnectionResult.SUCCESS

    override val canSave: Boolean
        get() = sharedFootprint.image != null

    override suspend fun save() {
        val createInfo = withContext(dispatchersProvider.ioDispatcher) {
            createEditFootprint.create(
                CreateFootprintInfo(
                    draftImageFile = sharedFootprint.image!!,
                    location = sharedFootprint.manualSelectedLocation ?: geolocationProvider.lastLocation,
                    comment = sharedFootprint.comment,
                    pinColor = sharedFootprint.pinColor
                ))
        }

        titleDataUpdaterProvider.updateLastFootprintUri(createInfo.lastFootprintImage)
        titleDataUpdaterProvider.updateTotalFootprints(createInfo.totalFootprints)
    }
}