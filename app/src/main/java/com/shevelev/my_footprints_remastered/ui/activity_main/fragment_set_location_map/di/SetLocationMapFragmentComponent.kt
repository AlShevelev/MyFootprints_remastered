package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view.SetLocationMapFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [SetLocationMapFragmentModuleBinds::class])
@SubFragmentScope
interface SetLocationMapFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SetLocationMapFragmentComponent
    }

    fun inject(fragment: SetLocationMapFragment)
}