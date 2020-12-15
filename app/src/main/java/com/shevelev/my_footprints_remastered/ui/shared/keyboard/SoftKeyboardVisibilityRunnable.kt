package com.shevelev.my_footprints_remastered.ui.shared.keyboard

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/** If we call showSoftInput while the views are being created,
 * there is a good chance that the view won't be active for IMM.
 * This class keeps running every 100 ms until the keyboard has been successfully shown.
 *
 * Idea of the class has got from here: https://stackoverflow.com/a/43516620
 */
class SoftKeyboardVisibilityRunnable(
    private val context: Context,
    private val targetView: View,
    private val isVisible: Boolean
) : Runnable {

    private val waitInterval = 100L     // ms

    override fun run() {
        if(isVisible) {
            showKeyboard()
        } else {
            hideKeyboard()
        }
    }

    private fun showKeyboard() {
        // Check view is focusable
        if (!(targetView.isFocusable && targetView.isFocusableInTouchMode)) {       // Non focusable view
            return
        }

        if (!targetView.requestFocus()) {         // Cannot focus on view
            targetView.postDelayed(this, waitInterval)
            return
        }

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (!imm.isActive(targetView)) {         // IMM is not active
            targetView.postDelayed(this, waitInterval)
            return
        }

        val isVisibilityUpdated = imm.showSoftInput(targetView, InputMethodManager.SHOW_FORCED)

        if (!isVisibilityUpdated) { // Unable to update visibility
            targetView.postDelayed(this, waitInterval)
        }
    }

    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val isVisibilityUpdated = imm.hideSoftInputFromWindow(targetView.windowToken, 0)

        if (!isVisibilityUpdated) { // Unable to update visibility
            targetView.postDelayed(this, waitInterval)
        }
    }
}