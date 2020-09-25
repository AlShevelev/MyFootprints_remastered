package com.shevelev.my_footprints_remastered.ui.activity_main.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.CropPhotoFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.EditPhotoFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view.SetLocationFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view.TitleFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import java.io.File
import javax.inject.Inject

@ActivityScope
class MainActivityNavigationImpl
@Inject
constructor() : MainActivityNavigation {
    override fun moveToCreateFootprint(fragment: TitleFragment) =
        fragment.findNavController().navigate(R.id.action_titleFragment_to_createFootprintFragment)

    override fun moveToSetLocation(fragment: CreateFootprintFragment) {
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_setLocationFragment)
    }

    override fun moveToSelectPhoto(fragment: CreateFootprintFragment) =
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_selectPhotoFragment)

    override fun moveToCropPhoto(fragment: CreateFootprintFragment, photo: File) {
        val bundle = bundleOf(CropPhotoFragment.FILE_PARAM_KEY to photo.absolutePath)
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_cropPhotoFragment, bundle)
    }

    override fun moveToEditPhoto(fragment: CreateFootprintFragment, photo: File) {
        val bundle = bundleOf(EditPhotoFragment.FILE_PARAM_KEY to photo.absolutePath)
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_editPhotoFragment, bundle)
    }

    override fun moveBack(fragment: Fragment) {
        fragment.findNavController().navigateUp()
    }

    override fun moveBackFromSetLocationToTitle(fragment: SetLocationFragment) {
        fragment.findNavController().popBackStack(R.id.titleFragment, false)
    }
}