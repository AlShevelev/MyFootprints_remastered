package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentCreateFootprintBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.CreateFootprintFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.ConfirmationDialog
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.OkDialog
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor.SelectColorDialog
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.location_settings.LocationSettingsHelper
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import kotlinx.android.synthetic.main.fragment_create_footprint.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class CreateFootprintFragment : FragmentBaseMVVM<FragmentCreateFootprintBinding, CreateFootprintFragmentViewModel>() {
    private val geolocationRequest = 8056
    private val footprintInterruptionRequest = 4216

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

    override fun inject(key: String) = App.injections.get<CreateFootprintFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<CreateFootprintFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is MoveToSelectPhoto -> moveToSelectPhotoWithPermissionCheck()
            is MoveToCropPhoto -> navigation.moveToCropPhoto(this, command.photo)
            is MoveToEditPhoto -> navigation.moveToEditPhoto(this, command.photo)
            is MoveToSetLocation -> navigation.moveToSetLocation(this)

            is AskAboutGeolocation -> ConfirmationDialog.show(geolocationRequest, this, R.string.enableLocationQuestion, R.string.goToSettings, R.string.notNow)
            is AskAboutFootprintInterruption -> ConfirmationDialog.show(footprintInterruptionRequest, this, R.string.footprintInterruptionQuery, R.string.interrupt, R.string.cancel)

            is OpenLocationSettings -> locationSettingsHelper.openSettings(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            OkDialog.REQUEST_CODE -> proceedMoveToSelectPhotoPermissionRequest()
            geolocationRequest -> if(!isCanceled) viewModel.onGotoLocationSettingsSelected()
            footprintInterruptionRequest -> if(!isCanceled) viewModel.onBackConfirmed()
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
        OkDialog.show(this, R.string.externalStorageExplanation)

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhotoDenied() = showMessage(R.string.externalStorageDenied)
}