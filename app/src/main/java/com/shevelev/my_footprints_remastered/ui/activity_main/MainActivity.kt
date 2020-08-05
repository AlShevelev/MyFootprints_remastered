package com.shevelev.my_footprints_remastered.ui.activity_main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.ui.activity_main.di.MainActivityComponent
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.ActivityBase

class MainActivity : ActivityBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigationBarButtonsColor()
    }

    override fun inject(key: String) = App.injections.get<MainActivityComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<MainActivityComponent>(key)

    /**
     * Make a navigation bar light with dark buttons
     */
    private fun setNavigationBarButtonsColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = Color.WHITE
            val decorView = window.decorView
            decorView.systemUiVisibility = decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}