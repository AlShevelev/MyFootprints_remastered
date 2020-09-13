package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view.SetLocationFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di.SetLocationStubFragmentComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [SetLocationFragmentModuleBinds::class])
@SubFragmentScope
interface SetLocationFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SetLocationFragmentComponent
    }

    fun inject(fragment: SetLocationFragment)
}