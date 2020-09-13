package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentSetLocationStubBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.di.SetLocationStubFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_stub.view_model.SetLocationStubFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM

class SetLocationStubFragment : FragmentBaseMVVM<FragmentSetLocationStubBinding, SetLocationStubFragmentViewModel>() {
    override fun provideViewModelType(): Class<SetLocationStubFragmentViewModel> = SetLocationStubFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_set_location_stub

    override fun linkViewModel(binding: FragmentSetLocationStubBinding, viewModel: SetLocationStubFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<SetLocationStubFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<SetLocationStubFragmentComponent>(key)
}