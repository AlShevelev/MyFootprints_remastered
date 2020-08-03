package com.shevelev.my_footprints_remastered.ui.main_activity.di

import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.title_fragment.di.TitleFragmentComponent
import dagger.Module

@Module(subcomponents = [TitleFragmentComponent::class, SelectPhotoFragmentComponent::class])
class MainActivityModuleChilds