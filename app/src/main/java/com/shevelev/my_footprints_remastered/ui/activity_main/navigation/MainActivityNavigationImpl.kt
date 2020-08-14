package com.shevelev.my_footprints_remastered.ui.activity_main.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.CropPhotoFragment
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

    override fun moveToSelectPhoto(fragment: CreateFootprintFragment) =
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_selectPhotoFragment)

    override fun moveToCropPhoto(fragment: CreateFootprintFragment, photo: File) {
        val bundle = bundleOf(CropPhotoFragment.FILE_PARAM_KEY to photo.absolutePath)
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_cropPhotoFragment, bundle)
    }

    override fun moveBack(fragment: Fragment) {
        fragment.findNavController().navigateUp()
    }
}