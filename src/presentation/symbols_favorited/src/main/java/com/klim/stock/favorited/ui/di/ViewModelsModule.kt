package com.klim.stock.favorited.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.favorited.ui.SymbolFavoritedViewModelImpl
import com.klim.stock.favorited.ui.api.SymbolFavoritedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SymbolFavoritedViewModel::class)
    fun bindViewModelToBase(viewModel: SymbolFavoritedViewModel): ViewModel

    @Binds
    fun bindViewModel(viewModel: SymbolFavoritedViewModelImpl): SymbolFavoritedViewModel

}