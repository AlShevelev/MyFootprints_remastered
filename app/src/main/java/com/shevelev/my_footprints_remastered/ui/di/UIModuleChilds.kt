package com.shevelev.my_footprints_remastered.ui.di

import com.shevelev.my_footprints_remastered.ui.activity_first_loading.di.FirstLoadingActivityComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.di.MainActivityComponent
import dagger.Module

@Module(subcomponents = [
    MainActivityComponent::class,
    FirstLoadingActivityComponent::class
])
class UIModuleChilds