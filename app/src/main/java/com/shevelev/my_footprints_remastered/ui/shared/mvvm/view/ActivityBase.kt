package com.shevelev.my_footprints_remastered.ui.shared.mvvm.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil

abstract class ActivityBase : AppCompatActivity() {
    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY"
    }

    private lateinit var injectionKey: String

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectionKey = savedInstanceState?.getString(INJECTION_KEY) ?: IdUtil.generateStringId()
        inject(injectionKey)

        setNavigationBarButtonsColor()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(INJECTION_KEY, injectionKey)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        if(isFinishing) {
            releaseInjection(injectionKey)
        }
        super.onDestroy()
    }

    protected abstract fun inject(key: String)

    protected abstract fun releaseInjection(key: String)

    /**
     * Make a navigation bar light with dark buttons
     */
    private fun setNavigationBarButtonsColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = Color.WHITE
            val decorView = window.decorView
            decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}