package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentGalleryOneBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.di.GalleryOneFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view_model.GalleryOneFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import javax.inject.Inject

class GalleryOneFragment : FragmentBaseMVVM<FragmentGalleryOneBinding, GalleryOneFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<GalleryOneFragmentViewModel> = GalleryOneFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_gallery_one

    override fun linkViewModel(binding: FragmentGalleryOneBinding, viewModel: GalleryOneFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<GalleryOneFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<GalleryOneFragmentComponent>(key)
}