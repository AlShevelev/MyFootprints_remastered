package com.shevelev.my_footprints_remastered.ui.activity_main.navigation

import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.GalleryGridFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view.GalleryPagesFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.MyWorldMapFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view.SetLocationFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view.TitleFragment
import java.io.File

interface MainActivityNavigation {
    fun moveToCreateFootprint(fragment: TitleFragment)

    fun moveToCreateFootprint(fragment: GalleryPagesFragment, oldFootprint: Footprint)

    fun moveToSetLocation(fragment: CreateFootprintFragment, oldFootprint: Footprint?, isImageUpdated: Boolean?)

    fun moveToSelectPhoto(fragment: CreateFootprintFragment)

    fun moveToCropPhoto(fragment: CreateFootprintFragment, photo: File)

    fun moveToEditPhoto(fragment: CreateFootprintFragment, photo: File)

    fun moveBack(fragment: Fragment)

    fun moveBackFromSetLocationToTitle(fragment: SetLocationFragment)

    fun moveBackFromSetLocationToPager(fragment: SetLocationFragment)

    fun moveBackFromPagerToTitle(fragment: GalleryPagesFragment)

    fun moveToGridGallery(fragment: TitleFragment)

    fun moveToOneGallery(fragment: GalleryGridFragment, footprints: List<Footprint>, currentIndex:Int)

    fun moveToOneGallery(fragment: MyWorldMapFragment, footprints: List<Footprint>, currentIndex:Int)

    fun moveToMyWorld(fragment: TitleFragment)
}