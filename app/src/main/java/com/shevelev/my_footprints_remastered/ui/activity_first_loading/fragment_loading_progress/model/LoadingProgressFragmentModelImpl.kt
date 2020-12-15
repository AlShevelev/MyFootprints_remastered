package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.model

import android.content.Context
import android.os.Handler
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.services.first_loading.FirstLoadingService
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessage
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver.FirstLoadingServiceMessageReceiver
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.dto.Event
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBaseImpl
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionHelper
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionInfo
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.resources.getStringFormatted
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadingProgressFragmentModelImpl
@Inject
constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val appContext: Context,
    messagesReceiver: FirstLoadingServiceMessageReceiver,
    private val eventsHandler: Handler,
    private val keyValueStorage: KeyValueStorageFacade,
    private val connectionHelper: ConnectionHelper
) : ModelBaseImpl(), LoadingProgressFragmentModel {

    private var eventsListener: ((Event) -> Unit)? = null

    private var warningWasShown = false

    init {
        messagesReceiver.setOnMessageListener { message ->
            when(message) {
                is FirstLoadingServiceMessage.Progress -> {
                    eventsListener?.invoke(Event.ShowMessage(
                        appContext.getStringFormatted(
                            R.string.firstLoadingListProgress,
                            message.current,
                            message.total
                        ))
                    )
                }

                is FirstLoadingServiceMessage.Success -> {
                    eventsListener?.invoke(Event.ShowMessage(appContext.getString(R.string.firstLoadingListSuccess)))
                    eventsListener?.invoke(Event.MoveToMainScreen)
                    eventsListener?.invoke(Event.StopLoadingAnimation)
                }

                is FirstLoadingServiceMessage.Fail -> {
                    eventsListener?.invoke(Event.ShowMessage(appContext.getString(R.string.firstLoadingListFail)))
                    eventsListener?.invoke(Event.ShowRestartButton)
                    eventsListener?.invoke(Event.StopLoadingAnimation)
                }

                is FirstLoadingServiceMessage.ListLoadStarted ->
                    eventsListener?.invoke(Event.ShowMessage(appContext.getString(R.string.firstLoadingListLoading)))

                is FirstLoadingServiceMessage.ListLoadCompleted ->
                    eventsListener?.invoke(Event.ShowMessage(appContext.getString(R.string.firstLoadingListLoaded)))
            }
        }
    }

    override fun setEventsListener(listener: ((Event) -> Unit)?) {
        eventsListener = listener
    }

    override suspend fun tryToStartService() {
        if(FirstLoadingService.serviceIsRunning) {
            return
        }

        val isStartLoadingCompleted = withContext(dispatchersProvider.ioDispatcher) {
            keyValueStorage.isStartLoadingCompleted()
        }

        if(isStartLoadingCompleted) {
            eventsListener?.invoke(Event.MoveToMainScreen)
        } else {
            if(warningWasShown) {
                FirstLoadingService.start(appContext, eventsHandler)
                eventsListener?.invoke(Event.HideRestartButton)
                eventsListener?.invoke(Event.StartLoadingAnimation)
            } else {
                warningWasShown = true
                when(connectionHelper.getConnectionInfo()) {
                    ConnectionInfo.NO_CONNECTION ->
                        eventsListener?.invoke(Event.ShowWarningDialog(appContext.getString(R.string.firstLoadingNoConnectionMessage)))

                    ConnectionInfo.MOBILE ->
                        eventsListener?.invoke(Event.ShowWarningDialog(appContext.getString(R.string.firstLoadingLTEMessage)))

                    ConnectionInfo.WI_FI ->
                        eventsListener?.invoke(Event.ShowWarningDialog(appContext.getString(R.string.firstLoadingStartMessage)))
                }
            }
        }
    }
}