package com.shevelev.my_footprints_remastered.ui.main_activity.navigation

import androidx.navigation.fragment.findNavController
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.title_fragment.view.TitleFragment
import com.shevelev.my_footprints_remastered.utils.di_scopes.ActivityScope
import javax.inject.Inject

@ActivityScope
class MainActivityNavigationImpl
@Inject
constructor() : MainActivityNavigation {
    override fun moveToSelectPhoto(fragment: TitleFragment) =
        fragment.findNavController().navigate(R.id.action_titleFragment_to_selectPhotoFragment)
}