package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.di

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver.FirstLoadingServiceMessageReceiver
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.receiver.FirstLoadingServiceMessageReceiverImpl
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.model.LoadingProgressFragmentModel
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.model.LoadingProgressFragmentModelImpl
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.view_model.LoadingProgressFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactory
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.FragmentViewModelFactoryImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelKey
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoadingProgressFragmentModuleBinds {
    @Binds
    abstract fun provideViewModelFactory(factory: FragmentViewModelFactoryImpl): FragmentViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(LoadingProgressFragmentViewModel::class)
    abstract fun provideViewModel(viewModel: LoadingProgressFragmentViewModel): ViewModel

    @Binds
    abstract fun provideModel(model: LoadingProgressFragmentModelImpl): LoadingProgressFragmentModel
}