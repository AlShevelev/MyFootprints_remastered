package com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentSelectPhotoBinding
import com.shevelev.my_footprints_remastered.ui.main_activity.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.di.SelectPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.select_photo_fragment.view_model.SelectPhotoFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.ViewCommand
import javax.inject.Inject

class SelectPhotoFragment : FragmentBaseMVVM<FragmentSelectPhotoBinding, SelectPhotoFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<SelectPhotoFragmentViewModel> = SelectPhotoFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_select_photo

    override fun linkViewModel(binding: FragmentSelectPhotoBinding, viewModel: SelectPhotoFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<SelectPhotoFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<SelectPhotoFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
        }
    }
}