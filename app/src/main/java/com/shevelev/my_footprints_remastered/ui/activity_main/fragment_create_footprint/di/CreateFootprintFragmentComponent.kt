package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [CreateFootprintFragmentModuleBinds::class])
@FragmentScope
interface CreateFootprintFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): CreateFootprintFragmentComponent
    }

    fun inject(fragment: CreateFootprintFragment)
}