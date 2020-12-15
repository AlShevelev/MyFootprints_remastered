package com.shevelev.my_footprints_remastered.ui.shared.keyboard

import android.view.View
import com.shevelev.my_footprints_remastered.ui.shared.standard_widet_ext.parentActivity

object KeyboardUtils {
    fun showKeyboard(someViewInWindow: View) = setKeyboardVisibility(someViewInWindow, true)

    fun hideKeyboard(someViewInWindow: View) = setKeyboardVisibility(someViewInWindow, false)

    /**
     * Note: it'll work only for android:windowSoftInputMode="adjustResize"
     */
    fun isKeyboardVisible(someViewInWindow: View): Boolean {
        val activity = someViewInWindow.parentActivity
        val activityRootView = activity!!.window.decorView.findViewById<View>(android.R.id.content)
        val screenView = activityRootView.rootView

        return activityRootView.height.toFloat() / screenView.height < 0.6
    }

    private fun setKeyboardVisibility(someViewInWindow: View, isVisible: Boolean) {
        if(someViewInWindow.isFocusable) {
            someViewInWindow.post(
                SoftKeyboardVisibilityRunnable(someViewInWindow.context, someViewInWindow, isVisible)
            )
        }
    }
}