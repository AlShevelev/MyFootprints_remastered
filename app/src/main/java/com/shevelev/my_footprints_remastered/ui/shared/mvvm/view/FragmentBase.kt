package com.shevelev.my_footprints_remastered.ui.shared.mvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil

abstract class FragmentBase: Fragment() {
    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY"
    }

    private lateinit var injectionKey: String

    protected open val isBackHandlerEnabled = false

    private var isDestroyedBySystem = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectionKey = savedInstanceState?.getString(INJECTION_KEY) ?: IdUtil.generateStringId()
        inject(injectionKey)

        if(isBackHandlerEnabled) {
            requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(isBackHandlerEnabled) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        isDestroyedBySystem = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(INJECTION_KEY, injectionKey)
        isDestroyedBySystem = true
    }

    override fun onDestroy() {
        super.onDestroy()

        if(!isDestroyedBySystem) {
            releaseInjection(injectionKey)
            final()
        }
    }

    open fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {}

    protected open fun inject(key: String) {}

    protected open fun releaseInjection(key: String) {}

    protected open fun onBackPressed() { }

    protected fun showMessage(text: String) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    protected fun showMessage(@StringRes text: Int) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    protected open fun final() {}
}