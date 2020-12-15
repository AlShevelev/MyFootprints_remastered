package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.view_model

import android.util.EventLogTags
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.dto.Event
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.model.LoadingProgressFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToMainScreen
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowOkDialog
import com.shevelev.my_footprints_remastered.ui.view_commands.StartAnimation
import com.shevelev.my_footprints_remastered.ui.view_commands.StopAnimation
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadingProgressFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: LoadingProgressFragmentModel
) : ViewModelBase<LoadingProgressFragmentModel>(dispatchersProvider, model) {

    private val _progressText = MutableLiveData<String>()
    val progressText: LiveData<String>
        get() = _progressText

    private val _restartButtonVisibility = MutableLiveData(View.INVISIBLE)
    val restartButtonVisibility: LiveData<Int>
        get() = _restartButtonVisibility

    init {
        model.setEventsListener { event ->
            when(event) {
                is Event.ShowMessage -> _progressText.value = event.text
                is Event.ShowRestartButton -> _restartButtonVisibility.value = View.VISIBLE
                is Event.HideRestartButton -> _restartButtonVisibility.value = View.INVISIBLE
                is Event.MoveToMainScreen -> sendCommand(MoveToMainScreen())
                is Event.ShowWarningDialog -> sendCommand(ShowOkDialog(event.text))
                is Event.StartLoadingAnimation -> sendCommand(StartAnimation())
                is Event.StopLoadingAnimation -> sendCommand(StopAnimation())
            }
        }

        startService()
    }

    fun onRestartClick() = startService()

    fun onCloseWarningDialog() = startService()

    private fun startService() {
        launch {
            model.tryToStartService()
        }
    }
}