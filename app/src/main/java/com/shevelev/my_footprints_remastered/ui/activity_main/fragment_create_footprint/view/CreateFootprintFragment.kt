package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentCreateFootprintBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.di.CreateFootprintFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view_model.CreateFootprintFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_commands.ViewCommand
import javax.inject.Inject

class CreateFootprintFragment : FragmentBaseMVVM<FragmentCreateFootprintBinding, CreateFootprintFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<CreateFootprintFragmentViewModel> = CreateFootprintFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_create_footprint

    override fun linkViewModel(binding: FragmentCreateFootprintBinding, viewModel: CreateFootprintFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<CreateFootprintFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<CreateFootprintFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
        }
    }
}