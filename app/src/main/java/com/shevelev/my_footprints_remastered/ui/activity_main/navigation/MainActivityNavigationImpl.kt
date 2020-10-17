package com.shevelev.my_footprints_remastered.ui.activity_main.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.CreateFootprintFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.CropPhotoFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.EditPhotoFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.GalleryGridFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view.GalleryPagesFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.MyWorldMapFragment
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

    override fun moveToCreateFootprint(fragment: GalleryPagesFragment, oldFootprint: Footprint) {
        val bundle = bundleOf().apply {
            putParcelable(CreateFootprintFragment.ARG_FOOTPRINT, oldFootprint)
        }
        fragment.findNavController().navigate(R.id.action_galleryOneFragment_to_createFootprintFragment, bundle)
    }

    override fun moveToSetLocation(fragment: CreateFootprintFragment, oldFootprint: Footprint?, isImageUpdated: Boolean?) {
        if(oldFootprint == null) {
            fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_setLocationFragment)
        } else {
            val bundle = bundleOf().apply {
                putParcelable(SetLocationFragment.ARG_FOOTPRINT, oldFootprint)
                putBoolean(SetLocationFragment.ARG_IS_IMAGE_UPDATED, isImageUpdated!!)
            }
            fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_setLocationFragment, bundle)
        }
    }

    override fun moveToSelectPhoto(fragment: CreateFootprintFragment) =
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_selectPhotoFragment)

    override fun moveToCropPhoto(fragment: CreateFootprintFragment, photo: File) {
        val bundle = bundleOf(CropPhotoFragment.ARG_FILE to photo.absolutePath)
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_cropPhotoFragment, bundle)
    }

    override fun moveToEditPhoto(fragment: CreateFootprintFragment, photo: File) {
        val bundle = bundleOf(EditPhotoFragment.ARG_FILE to photo.absolutePath)
        fragment.findNavController().navigate(R.id.action_createFootprintFragment_to_editPhotoFragment, bundle)
    }

    override fun moveBack(fragment: Fragment) {
        fragment.findNavController().navigateUp()
    }

    override fun moveBackFromSetLocationToTitle(fragment: SetLocationFragment) {
        fragment.findNavController().popBackStack(R.id.titleFragment, false)
    }

    override fun moveToGridGallery(fragment: TitleFragment) =
        fragment.findNavController().navigate(R.id.action_titleFragment_to_galleryGridFragment)

    override fun moveToOneGallery(fragment: GalleryGridFragment, footprints: List<Footprint>, currentIndex:Int) {
        val bundle = bundleOf().apply {
            putParcelableArrayList(GalleryPagesFragment.ARG_FOOTPRINTS_LIST, ArrayList(footprints))
            putInt(GalleryPagesFragment.ARG_CURRENT_FOOTPRINT_INDEX, currentIndex)
        }
        fragment.findNavController().navigate(R.id.action_galleryGridFragment_to_galleryOneFragment, bundle)
    }

    override fun moveToOneGallery(fragment: MyWorldMapFragment, footprints: List<Footprint>, currentIndex: Int) {
        val bundle = bundleOf().apply {
            putParcelableArrayList(GalleryPagesFragment.ARG_FOOTPRINTS_LIST, ArrayList(footprints))
            putInt(GalleryPagesFragment.ARG_CURRENT_FOOTPRINT_INDEX, currentIndex)
        }
        fragment.findNavController().navigate(R.id.action_myWorldFragment_to_galleryOneFragment, bundle)
    }

    override fun moveToMyWorld(fragment: TitleFragment) =
        fragment.findNavController().navigate(R.id.action_titleFragment_to_myWorldFragment)
}