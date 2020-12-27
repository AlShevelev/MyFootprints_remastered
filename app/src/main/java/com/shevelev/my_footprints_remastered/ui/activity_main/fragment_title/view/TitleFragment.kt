package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import coil.request.CachePolicy
import coil.request.RequestDisposable
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentTitleBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model.TitleFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.OkDialog
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class TitleFragment : FragmentBaseMVVM<FragmentTitleBinding, TitleFragmentViewModel>() {
    companion object {
        private const val LOCATION_EXPLANATION_REQUEST = 12359
    }

    private var lastFootprintDispose: RequestDisposable? = null

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<TitleFragmentViewModel> = TitleFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_title

    override fun linkViewModel(binding: FragmentTitleBinding, viewModel: TitleFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<TitleFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<TitleFragmentComponent>(key)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.lastFootprintUri.observe({viewLifecycleOwner.lifecycle}) { updateLastFootprintImage(it) }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        lastFootprintDispose?.takeIf { !it.isDisposed } ?.dispose()
        super.onDestroyView()
    }

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveToCreateFootprint -> moveToCreateFootprintWithPermissionCheck()
            is MoveToGridGallery -> navigation.moveToGridGallery(this)
            is MoveToMyWorld -> navigation.moveToMyWorld(this)
            is MoveToSettings -> navigation.moveToSettings(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            LOCATION_EXPLANATION_REQUEST -> proceedMoveToCreateFootprintPermissionRequest()
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun moveToCreateFootprint() = navigation.moveToCreateFootprint(this)

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun moveToSelectPhotoExplanation() = OkDialog.show(LOCATION_EXPLANATION_REQUEST, this, R.string.geolocationExplanation)

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun moveToSelectPhotoDenied() = showMessage(R.string.geolocationDenied)

    private fun updateLastFootprintImage(lastFootprintUri: Uri) {
        lastFootprintDispose?.takeIf { !it.isDisposed } ?.dispose()
        lastFootprintDispose = binding!!.lastFootprint.load(lastFootprintUri) {
            memoryCachePolicy(CachePolicy.DISABLED)
        }
    }
}