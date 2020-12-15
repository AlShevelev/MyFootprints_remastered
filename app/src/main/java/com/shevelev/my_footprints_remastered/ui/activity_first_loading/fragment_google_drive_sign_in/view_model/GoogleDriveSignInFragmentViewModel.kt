package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.view_model

import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveSignIn
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveSignInStatus
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.model.GoogleDriveSignInFragmentModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GoogleDriveSignInFragmentViewModel
@Inject
constructor(
    dispatchersProvider: DispatchersProvider,
    model: GoogleDriveSignInFragmentModel,
    private val googleDriveSignIn: GoogleDriveSignIn
) : ViewModelBase<GoogleDriveSignInFragmentModel>(dispatchersProvider, model) {

    init {
        launch {
            googleDriveSignIn.status.collect {
                processGoogleDriveSignInStatus(it)
            }
        }

        googleDriveSignIn.startSignIn()
    }

    fun onGoogleDriveExplanationClosed() = sendCommand(StartSignInToGoogleDrive)

    fun onGoogleDriveFailClosed() = sendCommand(ProcessSignInToGoogleDriveFail)

    private fun processGoogleDriveSignInStatus(status: GoogleDriveSignInStatus) {
        when(status) {
            GoogleDriveSignInStatus.SHOW_EXPLANATION -> sendCommand(ShowGoogleDriveExplanationDialog)
            GoogleDriveSignInStatus.FAIL -> sendCommand(ShowGoogleDriveFailDialog)
            GoogleDriveSignInStatus.SUCCESS -> sendCommand(ProcessSignInToGoogleDriveSuccess)
        }
    }
}