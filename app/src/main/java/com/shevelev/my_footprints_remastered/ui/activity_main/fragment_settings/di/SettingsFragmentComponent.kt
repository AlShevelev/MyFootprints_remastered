package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.view.SettingsFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [SettingsFragmentModuleBinds::class])
@FragmentScope
interface SettingsFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SettingsFragmentComponent
    }

    fun inject(fragment: SettingsFragment)
}