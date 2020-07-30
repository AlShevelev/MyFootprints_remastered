package com.shevelev.my_footprints_remastered.application.di

import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.ui.di.UIComponent
import com.shevelev.my_footprints_remastered.utils.di_scopes.ApplicationScope
import dagger.Component

@Component(modules = [AppModule::class, AppModuleBinds::class, AppModuleChilds::class])
@ApplicationScope
interface AppComponent {
    val ui: UIComponent.Builder

    fun inject(app: App)
}