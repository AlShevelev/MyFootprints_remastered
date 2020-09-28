package com.shevelev.my_footprints_remastered.ui.activity_main.navigation

import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.GalleryGridFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view.SetLocationFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view.TitleFragment
import java.io.File

interface MainActivityNavigation {
    fun moveToCreateFootprint(fragment: TitleFragment)

    fun moveToSetLocation(fragment: CreateFootprintFragment)

    fun moveToSelectPhoto(fragment: CreateFootprintFragment)

    fun moveToCropPhoto(fragment: CreateFootprintFragment, photo: File)

    fun moveToEditPhoto(fragment: CreateFootprintFragment, photo: File)

    fun moveBack(fragment: Fragment)

    fun moveBackFromSetLocationToTitle(fragment: SetLocationFragment)

    fun moveToGridGallery(fragment: TitleFragment)

    fun moveToOneGallery(fragment: GalleryGridFragment)
}