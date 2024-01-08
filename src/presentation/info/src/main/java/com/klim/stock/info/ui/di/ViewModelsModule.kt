package com.klim.stock.info.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.info.ui.presentation.InfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    fun bindViewModel(viewModel: InfoViewModel): ViewModel

}