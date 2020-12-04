package com.shevelev.my_footprints_remastered.ui.activity_main

import android.os.Bundle
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.ui.activity_main.di.MainActivityComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderApp
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.ActivityBase
import javax.inject.Inject

class MainActivity : ActivityBase() {
    @Inject
    internal lateinit var geolocationProvider: GeolocationProviderApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun inject(key: String) = App.injections.get<MainActivityComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<MainActivityComponent>(key)

    override fun onStart() {
        super.onStart()
        geolocationProvider.onAppActive()
    }

    override fun onStop() {
        super.onStop()
        geolocationProvider.onAppInactive()
    }
}