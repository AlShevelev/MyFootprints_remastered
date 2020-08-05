package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view.TitleFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [TitleFragmentModuleBinds::class])
@FragmentScope
interface TitleFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): TitleFragmentComponent
    }

    fun inject(fragment: TitleFragment)
}