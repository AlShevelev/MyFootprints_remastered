package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.databinding.FragmentGalleryPagesBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.di.GalleryPagesFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view.pager.GalleryPagesAdapter
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view_model.GalleryPagesFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.map.MapDialog
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowMapDialog
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import kotlinx.android.synthetic.main.fragment_gallery_pages.*
import javax.inject.Inject

class GalleryPagesFragment : FragmentBaseMVVM<FragmentGalleryPagesBinding, GalleryPagesFragmentViewModel>() {
    companion object {
        const val ARG_FOOTPRINTS_LIST = "ARG_FOOTPRINTS_LIST"
        const val ARG_CURRENT_FOOTPRINT_INDEX = "ARG_CURRENT_FOOTPRINT_INDEX"
    }

    private lateinit var galleryPagesAdapter: GalleryPagesAdapter

    private var needToUpdatePagerView: Boolean = false

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<GalleryPagesFragmentViewModel> = GalleryPagesFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_gallery_pages

    override fun linkViewModel(binding: FragmentGalleryPagesBinding, viewModel: GalleryPagesFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) {
        val footprints = requireArguments().getParcelableArrayList<Footprint>(ARG_FOOTPRINTS_LIST)!!.toList()
        val footprintIndex = requireArguments().getInt(ARG_CURRENT_FOOTPRINT_INDEX)

        App.injections.get<GalleryPagesFragmentComponent>(key, footprints, footprintIndex).inject(this)
    }

    override fun releaseInjection(key: String) = App.injections.release<GalleryPagesFragmentComponent>(key)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onViewReady()

        needToUpdatePagerView = true

        viewModel.items.observe({viewLifecycleOwner.lifecycle}) { updateGallery(it) }
        viewModel.currentIndex.observe({viewLifecycleOwner.lifecycle}) { pager.setCurrentItem(it, false) }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.onPageSelected(position)
            }
        })
    }

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is ShowMapDialog -> MapDialog.show(this, command.footprint)
        }
    }

    private fun updateGallery(items: List<VersionedListItem>) {
        if(!::galleryPagesAdapter.isInitialized) {
            galleryPagesAdapter = GalleryPagesAdapter(this)
        }

        if(needToUpdatePagerView) {
            pager.adapter = galleryPagesAdapter
            needToUpdatePagerView = false
        }

        galleryPagesAdapter.updateItems(items)
    }
}