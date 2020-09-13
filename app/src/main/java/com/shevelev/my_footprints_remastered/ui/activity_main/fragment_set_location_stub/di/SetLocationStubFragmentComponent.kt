package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.view.SetLocationStubFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.SubFragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [SetLocationStubFragmentModuleBinds::class])
@SubFragmentScope
interface SetLocationStubFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SetLocationStubFragmentComponent
    }

    fun inject(fragment: SetLocationStubFragment)
}