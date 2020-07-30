package com.shevelev.my_footprints_remastered.ui.title_fragment.di

import com.shevelev.my_footprints_remastered.ui.title_fragment.view.TitleFragment
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