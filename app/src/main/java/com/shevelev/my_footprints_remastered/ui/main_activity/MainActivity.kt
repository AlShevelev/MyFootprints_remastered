package com.shevelev.my_footprints_remastered.ui.main_activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.my_footprints_remastered.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigationBarButtonsColor()
    }

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