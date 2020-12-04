package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.view

import android.content.Intent
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentGoogleDriveSignInBinding
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveSignIn
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.FirstLoadingActivity
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.di.GoogleDriveSignInFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.view_model.GoogleDriveSignInFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.OkDialog
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import javax.inject.Inject

class GoogleDriveSignInFragment : FragmentBaseMVVM<FragmentGoogleDriveSignInBinding, GoogleDriveSignInFragmentViewModel>() {
    companion object {
        private const val GD_EXPLANATION_REQUEST = 7065
        private const val GD_FAIL_REQUEST = 9071

        fun newInstance() = GoogleDriveSignInFragment()
    }

    @Inject
    internal lateinit var googleDriveSignIn: GoogleDriveSignIn

    override fun provideViewModelType(): Class<GoogleDriveSignInFragmentViewModel> =
        GoogleDriveSignInFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_google_drive_sign_in

    override fun linkViewModel(binding: FragmentGoogleDriveSignInBinding, viewModel: GoogleDriveSignInFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<GoogleDriveSignInFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<GoogleDriveSignInFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is ShowGoogleDriveExplanationDialog -> OkDialog.show(GD_EXPLANATION_REQUEST, this, R.string.googleDriveExplanation)
            is ShowGoogleDriveFailDialog -> OkDialog.show(GD_FAIL_REQUEST, this, R.string.googleDriveFail)
            is StartSignInToGoogleDrive -> googleDriveSignIn.continueSignIn(this)
            is ProcessSignInToGoogleDriveFail -> (activity as? FirstLoadingActivity)?.onGoogleDriveSignInFail()
            is ProcessSignInToGoogleDriveSuccess -> (activity as? FirstLoadingActivity)?.onGoogleDriveSignInSuccess()
        }
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            GD_EXPLANATION_REQUEST -> viewModel.onGoogleDriveExplanationClosed()
            GD_FAIL_REQUEST -> viewModel.onGoogleDriveFailClosed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleDriveSignIn.processSignInActivityResult(requestCode, resultCode, data)
    }
}