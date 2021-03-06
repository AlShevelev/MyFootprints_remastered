package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.databinding.FragmentSetLocationBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.di.SetLocationFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location.view_model.SetLocationFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view.SetLocationMapFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.view.SetLocationStubFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.*
import javax.inject.Inject

class SetLocationFragment : FragmentBaseMVVM<FragmentSetLocationBinding, SetLocationFragmentViewModel>() {
    companion object {
        const val ARG_FOOTPRINT = "ARG_FOOTPRINT"
        const val ARG_IS_IMAGE_UPDATED = "ARG_IS_IMAGE_UPDATED"
    }

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<SetLocationFragmentViewModel> = SetLocationFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_set_location

    override fun linkViewModel(binding: FragmentSetLocationBinding, viewModel: SetLocationFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) {
        val footprint = arguments?.getParcelable<Footprint>(ARG_FOOTPRINT)
        val isImageUpdated = arguments?.getBoolean(ARG_IS_IMAGE_UPDATED)
        App.injections.get<SetLocationFragmentComponent>(key, footprint, isImageUpdated).inject(this)
    }

    override fun releaseInjection(key: String) = App.injections.release<SetLocationFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is AddStubFragment -> addStub()
            is AddMapFragment -> addMap()
            is MoveBackFromSetLocationToTitle -> navigation.moveBackFromSetLocationToTitle(this)
            is MoveBackFromSetLocationToPager -> navigation.moveBackFromSetLocationToPager(this)
        }
    }

    private fun addStub() {
        val fragment = SetLocationStubFragment()
        childFragmentManager.beginTransaction().add(R.id.mapContainer, fragment).commit()
    }

    private fun addMap() {
        val fragment = SetLocationMapFragment()
        childFragmentManager.beginTransaction().add(R.id.mapContainer, fragment).commit()
    }
}