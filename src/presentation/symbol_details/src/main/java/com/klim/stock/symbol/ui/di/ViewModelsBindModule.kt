package com.klim.stock.symbol.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.symbol.ui.presentation.SymbolDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(SymbolDetailsViewModel::class)
    fun bindViewModel(viewModel: SymbolDetailsViewModel): ViewModel

}