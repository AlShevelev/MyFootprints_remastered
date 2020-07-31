package com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.ShowMessageResCommand
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.ViewCommand
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class ViewModelBase<TModel: ModelBase>
constructor(
    dispatchersProvider: DispatchersProvider,
    _model: TModel? = null
) : ViewModel(), CoroutineScope {
    private val scopeJob: Job = SupervisorJob()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)
    }

    @Suppress("UNCHECKED_CAST")
    protected val model: TModel = _model ?: ModelBaseImpl() as TModel

    /**
     * Context of this scope.
     */
    override val coroutineContext: CoroutineContext = scopeJob + dispatchersProvider.uiDispatcher + errorHandler

    /**
     * Direct command for view
     */
    protected val _command = SingleLiveData<ViewCommand>()
    val command: LiveData<ViewCommand>  = _command

    /**
     * On configuration change we need to show dialog if it wasn't closed.
     * That's why we can't use [_command]
     */
    val dialogCommands: MutableLiveData<ViewCommand> = MutableLiveData()

    @CallSuper
    override fun onCleared() {
        scopeJob.cancel()
        super.onCleared()
    }

    private fun handleError(error: Throwable){
        Timber.e(error)
        _command.value = ShowMessageResCommand(R.string.generalError)
    }
}