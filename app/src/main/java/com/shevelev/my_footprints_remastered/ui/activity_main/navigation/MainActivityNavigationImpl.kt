package com.shevelev.my_footprints_remastered.ui.activity_main.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view.TitleFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import javax.inject.Inject

@ActivityScope
class MainActivityNavigationImpl
@Inject
constructor() : MainActivityNavigation {
    override fun moveToCreateFootprint(fragment: TitleFragment) =
        fragment.findNavController().navigate(R.id.action_titleFragment_to_createFootprintFragment)

    override fun moveToSelectPhoto(fragment: CreateFootprintFragment) =
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_selectPhotoFragment)

    override fun moveBack(fragment: Fragment) {
        fragment.findNavController().navigateUp()
    }
}