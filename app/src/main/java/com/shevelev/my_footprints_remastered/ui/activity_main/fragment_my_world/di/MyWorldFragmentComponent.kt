package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.view.MyWorldFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.di.MyWorldMapFragmentComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [MyWorldFragmentModuleBinds::class, MyWorldFragmentModuleChilds::class])
@FragmentScope
interface MyWorldFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): MyWorldFragmentComponent
    }

    val myWorldMapFragmentComponent: MyWorldMapFragmentComponent.Builder

    fun inject(fragment: MyWorldFragment)
}