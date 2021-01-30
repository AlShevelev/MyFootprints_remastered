package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.image_type_detector.ImageTypeDetector
import com.shevelev.my_footprints_remastered.shared_use_cases.creata_update_footprint.CreateUpdateFootprint
import com.shevelev.my_footprints_remastered.storages.files.BitmapFilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.CreateFootprintFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.UpdateFootprintModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.InsertFootprintModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.shared_footprint.SharedFootprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.CreateFootprintFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.InsertFootprintViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.UpdateFootprintViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.last.LastFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.fragments_data_flow.update.UpdateFootprintDataFlowProvider
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderManager
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * @param oldFootprint the value is null if a footprint is created and not null if a footprint is edited
 */
@Module
class CreateFootprintFragmentModule(
    private val oldFootprint: Footprint?
) {
    @Provides
    @FragmentScope
    fun provideSharedFootprint() = SharedFootprint()

    @IntoMap
    @ViewModelKey(CreateFootprintFragmentViewModel::class)
    @Provides
    fun provideViewModel(
        appContext: Context,
        dispatchersProvider: DispatchersProvider,
        model: CreateFootprintFragmentModel): ViewModel =
        if(oldFootprint == null) {
            InsertFootprintViewModel(
                appContext,
                dispatchersProvider,
                model
            )
        } else {
            UpdateFootprintViewModel(
                appContext,
                dispatchersProvider,
                model,
                oldFootprint
            )
        }


    @Provides
    fun provideModel(
        appContext: Context,
        dispatchersProvider: DispatchersProvider,
        dataBridge: CreateFootprintFragmentDataBridge,
        geolocationProvider: GeolocationProviderManager,
        sharedFootprint: SharedFootprint,
        filesHelper: BitmapFilesHelper,
        createUpdateFootprint: CreateUpdateFootprint,
        lastFootprintDataFlowProvider: LastFootprintDataFlowProvider,
        keyValueStorageFacade: KeyValueStorageFacade,
        updateFootprintDataFlowProvider: UpdateFootprintDataFlowProvider,
        imageTypeDetector: ImageTypeDetector
    ): CreateFootprintFragmentModel =
        if(oldFootprint == null) {
            InsertFootprintModel(
                appContext,
                dispatchersProvider,
                dataBridge,
                geolocationProvider,
                sharedFootprint,
                filesHelper,
                createUpdateFootprint,
                lastFootprintDataFlowProvider,
                keyValueStorageFacade,
                imageTypeDetector
            )
        } else {
            UpdateFootprintModel(
                appContext,
                dispatchersProvider,
                dataBridge,
                geolocationProvider,
                sharedFootprint,
                filesHelper,
                createUpdateFootprint,
                lastFootprintDataFlowProvider,
                keyValueStorageFacade,
                oldFootprint,
                updateFootprintDataFlowProvider,
                imageTypeDetector
            )
        }
}