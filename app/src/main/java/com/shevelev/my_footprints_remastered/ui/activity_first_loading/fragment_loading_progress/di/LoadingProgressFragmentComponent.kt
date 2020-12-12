package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.di

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.view.LoadingProgressFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [LoadingProgressFragmentModuleBinds::class])
@FragmentScope
interface LoadingProgressFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): LoadingProgressFragmentComponent
    }

    fun inject(fragment: LoadingProgressFragment)
}