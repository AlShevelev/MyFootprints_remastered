package com.shevelev.my_footprints_remastered.ui.di

import com.shevelev.my_footprints_remastered.ui.main_activity.di.MainActivityComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.UIScope
import dagger.Subcomponent

@Subcomponent(modules = [UIModuleChilds::class])
@UIScope
interface UIComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): UIComponent
    }

    val mainActivity: MainActivityComponent.Builder
}