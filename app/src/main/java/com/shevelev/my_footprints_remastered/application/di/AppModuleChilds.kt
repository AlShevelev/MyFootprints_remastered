package com.shevelev.my_footprints_remastered.application.di

import com.shevelev.my_footprints_remastered.ui.di.UIComponent
import dagger.Module

@Module(subcomponents = [
    UIComponent::class
])
class AppModuleChilds