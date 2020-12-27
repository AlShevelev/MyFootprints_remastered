package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentGalleryGridBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.di.GalleryGridFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view.grid.FootprintGridAdapter
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_grid.view_model.GalleryGridFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToOneGallery
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import com.shevelev.my_footprints_remastered.utils.resources.isPortrait
import javax.inject.Inject

class GalleryGridFragment : FragmentBaseMVVM<FragmentGalleryGridBinding, GalleryGridFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    private val gridAdapter by lazy { FootprintGridAdapter(viewModel).apply { setHasStableIds(true) } }
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun provideViewModelType(): Class<GalleryGridFragmentViewModel> = GalleryGridFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_gallery_grid

    override fun linkViewModel(binding: FragmentGalleryGridBinding, viewModel: GalleryGridFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<GalleryGridFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<GalleryGridFragmentComponent>(key)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.items.observe({viewLifecycleOwner.lifecycle}) { setListItems(it) }
        initList()
    }

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is MoveToOneGallery -> navigation.moveToOneGallery(this, command.footprints, command.currentIndex)
        }
    }

    private fun initList() {
        with(binding!!) {
            val cols = if(requireContext().isPortrait()) 2 else 4
            gridLayoutManager = GridLayoutManager(context, cols)

            footprintGrid.isSaveEnabled = false
            footprintGrid.itemAnimator = null
            footprintGrid.adapter = gridAdapter
            footprintGrid.layoutManager = gridLayoutManager
        }
    }

    private fun setListItems(items: List<VersionedListItem>) = gridAdapter.update(items)
}