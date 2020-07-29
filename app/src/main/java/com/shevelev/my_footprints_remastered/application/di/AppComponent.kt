package com.shevelev.my_footprints_remastered.application.di

import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.di_scopes.ApplicationScope
import dagger.Component

@Component(modules = [AppModule::class])
@ApplicationScope
interface AppComponent {
//    val mainActivity: MainActivityComponent.Builder

    fun inject(app: App)
}