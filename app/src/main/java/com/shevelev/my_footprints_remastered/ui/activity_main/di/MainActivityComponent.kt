package com.shevelev.my_footprints_remastered.ui.activity_main.di

import com.shevelev.my_footprints_remastered.ui.activity_main.MainActivity
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
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
    val selectPhotoFragment: SelectPhotoFragmentComponent.Builder
    val createFootprintFragment: CreateFootprintFragmentComponent.Builder

    fun inject(activity: MainActivity)
}