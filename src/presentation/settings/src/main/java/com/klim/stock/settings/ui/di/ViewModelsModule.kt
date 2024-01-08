package com.klim.stock.settings.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.settings.ui.presentation.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindViewModel(viewModel: SettingsViewModel): ViewModel

}