package com.shevelev.my_footprints_remastered.ui.main_activity.navigation

import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.ui.title_fragment.view.TitleFragment

interface MainActivityNavigation {
    fun moveToSelectPhoto(fragment: TitleFragment)

    fun moveBack(fragment: Fragment)
}