package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di

import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [CreateFootprintFragmentModuleBinds::class, CreateFootprintFragmentModuleChilds::class])
@FragmentScope
interface CreateFootprintFragmentComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): CreateFootprintFragmentComponent
    }

    val selectPhotoFragment: SelectPhotoFragmentComponent.Builder

    fun inject(fragment: CreateFootprintFragment)
}