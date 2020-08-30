package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.view.CreateFootprintMapFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [CreateFootprintMapFragmentModuleBinds::class])
@SubFragmentScope
interface CreateFootprintMapFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): CreateFootprintMapFragmentComponent
    }

    fun inject(fragment: CreateFootprintMapFragment)
}