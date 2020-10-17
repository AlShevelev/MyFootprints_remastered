package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di

import android.content.Context
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.shared_use_cases.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model.InsertSetLocationFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model.SetLocationFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.model.UpdateSetLocationFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderData
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import dagger.Module
import dagger.Provides

/**
 * @param oldFootprint the value is null if a footprint is created and not null if a footprint is edited
 */
@Module
class SetLocationFragmentModule(
    private val oldFootprint: Footprint?,
    private val isImageUpdated: Boolean?
) {

    @Provides
    fun provideModel(
        appContext: Context,
        dispatchersProvider: DispatchersProvider,
        sharedFootprint: SharedFootprint,
        createUpdateFootprint: CreateUpdateFootprint,
        geolocationProvider: GeolocationProviderData,
        lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
        updateFootprintDataFlowProvider: UpdateFootprintDataFlowProvider
    ): SetLocationFragmentModel =
        if(oldFootprint == null) {
            InsertSetLocationFragmentModel(
                appContext,
                dispatchersProvider,
                sharedFootprint,
                createUpdateFootprint,
                geolocationProvider,
                lastFootprintDataFlowProvider
            )
        } else {
            UpdateSetLocationFragmentModel(
                appContext,
                dispatchersProvider,
                sharedFootprint,
                createUpdateFootprint,
                geolocationProvider,
                lastFootprintDataFlowProvider,
                oldFootprint,
                isImageUpdated!!,
                updateFootprintDataFlowProvider
            )
        }
}