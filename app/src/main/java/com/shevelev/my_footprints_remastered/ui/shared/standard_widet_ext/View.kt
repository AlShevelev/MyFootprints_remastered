package com.shevelev.my_footprints_remastered.ui.shared.standard_widet_ext

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Return parent activity for a view
 */
val View.parentActivity: AppCompatActivity?
    get() {
        var context = this.context
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }
