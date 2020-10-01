package com.shevelev.my_footprints_remastered.ui.shared.dialogs.map

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BottomSheetDialogFragmentBase: BottomSheetDialogFragment(), CoroutineScope {
    companion object {
        private const val INJECTION_KEY = "INJECTION_KEY"
    }

    private lateinit var injectionKey: String

    private var isDestroyedBySystem = false

    private val scopeJob: Job = SupervisorJob()

    private val errorHandler = CoroutineExceptionHandler { _, ex ->
        Timber.e(ex)
    }

    /**
     * Context of this scope.
     */
    override val coroutineContext: CoroutineContext by lazy { scopeJob + dispatchersProvider.uiDispatcher + errorHandler }

    @Inject
    protected lateinit var dispatchersProvider: DispatchersProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectionKey = savedInstanceState?.getString(INJECTION_KEY) ?: IdUtil.generateStringId()
        inject(injectionKey)
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
            scopeJob.cancel()
            releaseInjection(injectionKey)
        }
    }

    protected open fun inject(key: String) {}

    protected open fun releaseInjection(key: String) {}
}