package com.klim.stock.presentation.main.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.presentation.main.MainActivityViewModelImpl
import com.klim.stock.presentation.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindViewModelIntoMap(viewModel: MainActivityViewModel): ViewModel

    @Binds
    fun bindViewModel(viewModel: MainActivityViewModelImpl): MainActivityViewModel

}