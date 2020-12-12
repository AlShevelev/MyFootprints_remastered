package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentLoadingProgressBinding
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.FirstLoadingActivity
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.di.LoadingProgressFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.view_model.LoadingProgressFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.OkDialog
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToMainScreen
import com.shevelev.my_footprints_remastered.ui.view_commands.ShowOkDialog
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import kotlinx.android.synthetic.main.fragment_loading_progress.*

class LoadingProgressFragment : FragmentBaseMVVM<FragmentLoadingProgressBinding, LoadingProgressFragmentViewModel>() {
    companion object {
        private const val WARNING_REQUEST = 9443
        fun newInstance() = LoadingProgressFragment()
    }

    override fun provideViewModelType(): Class<LoadingProgressFragmentViewModel> = LoadingProgressFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_loading_progress

    override fun linkViewModel(binding: FragmentLoadingProgressBinding, viewModel: LoadingProgressFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<LoadingProgressFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<LoadingProgressFragmentComponent>(key)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationProgress.apply {
            setBackgroundResource(R.drawable.ic_globe_animation)
            (animationProgress.background as AnimationDrawable).start()
        }
    }

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveToMainScreen -> (activity as? FirstLoadingActivity)?.moveToMainScreen()
            is ShowOkDialog -> OkDialog.show(WARNING_REQUEST, this, command.text)
        }
    }

    override fun onDialogResult(isCanceled: Boolean, requestCode: Int, data: Any?) {
        when(requestCode) {
            WARNING_REQUEST -> viewModel.onCloseWarningDialog()
        }
    }
}