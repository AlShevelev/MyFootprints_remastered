package com.shevelev.my_footprints_remastered.ui.title_fragment.view

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.api.clear
import coil.api.load
import coil.request.LoadRequest
import coil.request.RequestDisposable
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentTitleBinding
import com.shevelev.my_footprints_remastered.ui.main_activity.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.main_activity.view_commands.MoveToShowPhoto
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.OkDialog
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.ViewCommand
import com.shevelev.my_footprints_remastered.ui.title_fragment.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.ui.title_fragment.view_model.TitleFragmentViewModel
import kotlinx.android.synthetic.main.fragment_title.*
import permissions.dispatcher.*
import javax.inject.Inject

@RuntimePermissions
class TitleFragment : FragmentBaseMVVM<FragmentTitleBinding, TitleFragmentViewModel>() {
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
            is MoveToShowPhoto -> moveToSelectPhotoWithPermissionCheck()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            OkDialog.REQUEST_CODE -> proceedMoveToSelectPhotoPermissionRequest()
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhoto() = navigation.moveToSelectPhoto(this)

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhotoExplanation() =
        OkDialog.show(this, R.string.externalStorageExplanation)

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal fun moveToSelectPhotoDenied() = Toast.makeText(context, R.string.externalStorageDenied, Toast.LENGTH_LONG).show()

    private fun updateLastFootprintImage(lastFootprintUri: Uri) {
        lastFootprintDispose?.takeIf { !it.isDisposed } ?.dispose()
        lastFootprintDispose = lastFootprint.load(lastFootprintUri)
    }
}