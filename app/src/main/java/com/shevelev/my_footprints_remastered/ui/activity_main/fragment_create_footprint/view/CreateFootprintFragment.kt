package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view

import android.Manifest
import android.os.Bundle
import android.view.View
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.databinding.FragmentCreateFootprintBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.CreateFootprintFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.ConfirmationDialog
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.OkDialog
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.location_settings.LocationSettingsHelper
import com.shevelev.my_footprints_remastered.ui.shared.keyboard.KeyboardUtils
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class CreateFootprintFragment : FragmentBaseMVVM<FragmentCreateFootprintBinding, CreateFootprintFragmentViewModel>() {
    companion object {
        const val ARG_FOOTPRINT = "ARG_FOOTPRINT"
        private const val EXPLANATION_REQUEST = 54848
        private const val GEOLOCATION_REQUEST = 8056
        private const val FOOTPRINT_INTERRUPTION_REQUEST = 4216
    }

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    @Inject
    internal lateinit var locationSettingsHelper: LocationSettingsHelper

    override val isBackHandlerEnabled: Boolean = true

    override fun provideViewModelType(): Class<CreateFootprintFragmentViewModel> = CreateFootprintFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_create_footprint

    override fun linkViewModel(binding: FragmentCreateFootprintBinding, viewModel: CreateFootprintFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) {
        val footprint = arguments?.getParcelable<Footprint>(ARG_FOOTPRINT)
        App.injections.get<CreateFootprintFragmentComponent>(key, footprint).inject(this)
    }

    override fun releaseInjection(key: String) = App.injections.release<CreateFootprintFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is MoveToSelectPhoto -> moveToSelectPhotoWithPermissionCheck()
            is MoveToCropPhoto -> navigation.moveToCropPhoto(this, command.photo)
            is MoveToEditPhoto -> navigation.moveToEditPhoto(this, command.photo)
            is MoveToSetLocation -> navigation.moveToSetLocation(this, command.oldFootprint, command.isImageUpdated)

            is AskAboutGeolocation ->
                ConfirmationDialog.show(
                    GEOLOCATION_REQUEST,
                    this,
                    R.string.enableLocationQuestion,
                    R.string.goToSettings,
                    R.string.notNow)

            is AskAboutFootprintInterruption ->
                ConfirmationDialog.show(
                    FOOTPRINT_INTERRUPTION_REQUEST,
                    this,
                    R.string.footprintInterruptionQuery,
                    R.string.interrupt,
                    R.string.cancel)

            is OpenLocationSettings -> locationSettingsHelper.openSettings(this)

            is HideSoftKeyboard -> binding?.commentText?.let { KeyboardUtils.hideKeyboard(it) }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            EXPLANATION_REQUEST -> proceedMoveToSelectPhotoPermissionRequest()
            GEOLOCATION_REQUEST -> if(!isCanceled) viewModel.onGotoLocationSettingsSelected()
            FOOTPRINT_INTERRUPTION_REQUEST -> if(!isCanceled) viewModel.onBackConfirmed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onViewCreated()
    }

    override fun onBackPressed() = viewModel.onBackClick()

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhoto() = navigation.moveToSelectPhoto(this)

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhotoExplanation() =
        OkDialog.show(EXPLANATION_REQUEST, this, R.string.externalStorageExplanation)

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhotoDenied() = showMessage(R.string.externalStorageDenied)
}