package com.shevelev.my_footprints_remastered.ui.main_activity.di

import com.shevelev.my_footprints_remastered.ui.main_activity.MainActivity
import com.shevelev.my_footprints_remastered.ui.title_fragment.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModuleBinds::class, MainActivityModuleChilds::class])
@ActivityScope
interface MainActivityComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }

    val titleFragment: TitleFragmentComponent.Builder

    fun inject(activity: MainActivity)
}