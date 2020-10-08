package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.MyWorldMapFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [MyWorldMapFragmentModuleBinds::class])
@SubFragmentScope
interface MyWorldMapFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): MyWorldMapFragmentComponent
    }

    fun inject(fragment: MyWorldMapFragment)
}