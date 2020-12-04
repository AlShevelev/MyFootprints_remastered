package com.shevelev.my_footprints_remastered.ui.di

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.di.FirstLoadingActivityComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.di.MainActivityComponent
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
    val firstLoadingActivity: FirstLoadingActivityComponent.Builder
}