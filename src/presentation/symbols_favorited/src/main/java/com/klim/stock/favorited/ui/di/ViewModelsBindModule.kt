package com.klim.stock.favorited.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.favorited.ui.SymbolFavoritedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(SymbolFavoritedViewModel::class)
    fun bindViewModel(viewModel: SymbolFavoritedViewModel): ViewModel

}