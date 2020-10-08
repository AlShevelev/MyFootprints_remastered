package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentMyWorldBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.di.MyWorldFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world.view_model.MyWorldFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_map.view.MyWorldMapFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_stub.MyWorldStubFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.AddMapFragment
import com.shevelev.my_footprints_remastered.ui.view_commands.AddStubFragment
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import javax.inject.Inject

class MyWorldFragment : FragmentBaseMVVM<FragmentMyWorldBinding, MyWorldFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<MyWorldFragmentViewModel> = MyWorldFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_my_world

    override fun linkViewModel(binding: FragmentMyWorldBinding, viewModel: MyWorldFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<MyWorldFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<MyWorldFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is AddStubFragment -> addStub()
            is AddMapFragment -> addMap()
        }
    }

    private fun addStub() {
        val fragment = MyWorldStubFragment()
        childFragmentManager.beginTransaction().add(R.id.mapContainer, fragment).commit()
    }

    private fun addMap() {
        val fragment = MyWorldMapFragment()
        childFragmentManager.beginTransaction().add(R.id.mapContainer, fragment).commit()
    }
}