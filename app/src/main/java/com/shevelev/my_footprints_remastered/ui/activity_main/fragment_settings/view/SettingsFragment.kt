package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.view

import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.databinding.FragmentSettingsBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.di.SettingsFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_settings.view_model.SettingsFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.external_intents.email.SendEmailHelper
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveBack
import com.shevelev.my_footprints_remastered.ui.view_commands.OpenEmail
import com.shevelev.my_footprints_remastered.ui.view_commands.ViewCommand
import dagger.Lazy
import javax.inject.Inject

class SettingsFragment : FragmentBaseMVVM<FragmentSettingsBinding, SettingsFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    @Inject
    internal lateinit var sendEmailHelper: Lazy<SendEmailHelper>

    override fun provideViewModelType(): Class<SettingsFragmentViewModel> = SettingsFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_settings

    override fun linkViewModel(binding: FragmentSettingsBinding, viewModel: SettingsFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<SettingsFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<SettingsFragmentComponent>(key)

    override fun processViewCommand(command: ViewCommand) {
        when(command) {
            is MoveBack -> navigation.moveBack(this)
            is OpenEmail -> sendEmailHelper.get().startSendEmail(command.emailTo, this)
        }
    }
}

