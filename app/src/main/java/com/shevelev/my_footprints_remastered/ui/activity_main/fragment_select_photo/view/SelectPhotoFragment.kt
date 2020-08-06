package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentSelectPhotoBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view.grid.PhotoGridAdapter
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.view_model.SelectPhotoFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.camera.CameraHelper
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.gallery.GalleryHelper
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.OpenCamera
import com.shevelev.my_footprints_remastered.ui.view_commands.OpenGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import kotlinx.android.synthetic.main.fragment_select_photo.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class SelectPhotoFragment : FragmentBaseMVVM<FragmentSelectPhotoBinding, SelectPhotoFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    @Inject
    internal lateinit var cameraHelper: CameraHelper

    @Inject
    internal lateinit var galleryHelper: GalleryHelper

    private lateinit var gridAdapter: PhotoGridAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun provideViewModelType(): Class<SelectPhotoFragmentViewModel> = SelectPhotoFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_select_photo

    override fun linkViewModel(binding: FragmentSelectPhotoBinding, viewModel: SelectPhotoFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<SelectPhotoFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<SelectPhotoFragmentComponent>(key)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.items.observe({viewLifecycleOwner.lifecycle}) { setListItems(it) }
    }

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is OpenCamera -> openCameraWithPermissionCheck()
            is OpenGallery -> openGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!cameraHelper.processCameraPhotoResult(requestCode, resultCode) { viewModel.onCameraImageCaptured(it) }) {
            galleryHelper.processCameraPhotoResult(requestCode, resultCode, data) { viewModel.onGalleryImageCaptured(it) }
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    internal fun openCamera() {
        if(!cameraHelper.takeCameraPhoto(this)) {
            showMessage(R.string.noCamera)
        }
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    internal fun moveToSelectPhotoDenied() = showMessage(R.string.cameraDenied)

    private fun setListItems(items: List<VersionedListItem>) {
        if(!::gridAdapter.isInitialized) {
            gridLayoutManager = GridLayoutManager(context, 4)

            gridAdapter = PhotoGridAdapter(viewModel)
            gridAdapter.setHasStableIds(false)

            photosGrid.isSaveEnabled = false
            photosGrid.itemAnimator = null
            photosGrid.layoutManager = gridLayoutManager
            photosGrid.adapter = gridAdapter
        }

        gridAdapter.update(items)
    }

    private fun openGallery() {
        if(!galleryHelper.takePhoto(this)) {
            showMessage(R.string.noGallery)
        }
    }
}