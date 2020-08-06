package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import coil.request.RequestDisposable
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentTitleBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.di.TitleFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model.TitleFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToCreateFootprint
import kotlinx.android.synthetic.main.fragment_title.*
import javax.inject.Inject

class TitleFragment : FragmentBaseMVVM<FragmentTitleBinding, TitleFragmentViewModel>() {
    private var lastFootprintDispose: RequestDisposable? = null

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<TitleFragmentViewModel> = TitleFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_title

    override fun linkViewModel(binding: FragmentTitleBinding, viewModel: TitleFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<TitleFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<TitleFragmentComponent>(key)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.lastFootprintUri.observe({viewLifecycleOwner.lifecycle}) { updateLastFootprintImage(it) }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        lastFootprintDispose?.takeIf { !it.isDisposed } ?.dispose()
        super.onDestroyView()
    }

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveToCreateFootprint -> moveToCreateFootprint()
        }
    }

    private fun moveToCreateFootprint() = navigation.moveToCreateFootprint(this)

    private fun updateLastFootprintImage(lastFootprintUri: Uri) {
        lastFootprintDispose?.takeIf { !it.isDisposed } ?.dispose()
        lastFootprintDispose = lastFootprint.load(lastFootprintUri)
    }
}