package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentCreateFootprintMapBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.di.CreateFootprintMapFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint_map.view_model.CreateFootprintMapFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import javax.inject.Inject

class CreateFootprintMapFragment : FragmentBaseMVVM<FragmentCreateFootprintMapBinding, CreateFootprintMapFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<CreateFootprintMapFragmentViewModel> = CreateFootprintMapFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_create_footprint_map

    override fun linkViewModel(binding: FragmentCreateFootprintMapBinding, viewModel: CreateFootprintMapFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<CreateFootprintMapFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<CreateFootprintMapFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
        }
    }
}