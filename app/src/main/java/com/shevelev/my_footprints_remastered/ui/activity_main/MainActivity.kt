package com.shevelev.my_footprints_remastered.ui.activity_main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.ui.activity_main.di.MainActivityComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.geolocation.GeolocationProviderApp
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.ActivityBase
import javax.inject.Inject

class MainActivity : ActivityBase() {
    companion object {
        private const val SYSTEM_UI_VISIBILITY_FLAGS = "SYSTEM_UI_VISIBILITY_FLAGS"
    }

    @Inject
    internal lateinit var geolocationProvider: GeolocationProviderApp

    private var systemUIVisibilityFlags: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        systemUIVisibilityFlags = savedInstanceState?.getInt(SYSTEM_UI_VISIBILITY_FLAGS) ?: window.decorView.systemUiVisibility

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SYSTEM_UI_VISIBILITY_FLAGS, systemUIVisibilityFlags)
    }

    fun setSystemUIVisibility(isVisible: Boolean) {
        if(isVisible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            window.decorView.systemUiVisibility = systemUIVisibilityFlags
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}