package com.klim.stock.di.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.ui.windows.MainActivityViewModel
import com.klim.stock.favorited.ui.SymbolFavoritedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsBindModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(SymbolFavoritedViewModel::class)
//    fun homeViewModel(viewModel: SymbolFavoritedViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(com.klim.stock.settings.ui.presentation.SettingsViewModel::class)
//    fun settingsViewModel(viewModel: com.klim.stock.settings.ui.presentation.SettingsViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(InfoViewModel::class)
//    fun infoViewModel(viewModel: InfoViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(com.klim.stock.search.ui.search.SearchViewModel::class)
//    fun searchViewModel(viewModel: com.klim.stock.search.ui.search.SearchViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(SymbolDetailsViewModel::class)
//    fun symbolDetailsViewModel(viewModel: SymbolDetailsViewModel): ViewModel
}