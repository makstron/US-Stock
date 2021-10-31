package com.klim.di.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klim.symbol_details.presentation.SymbolDetailsViewModel
import com.klim.us_stock.ui.windows.MainActivityViewModel
import com.klim.us_stock.ui.windows.home.SymbolViewModel
import com.klim.us_stock.ui.windows.info.InfoViewModel
import com.klim.us_stock.ui.windows.search.SearchViewModel
import com.klim.us_stock.ui.windows.settings.SettingsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SymbolViewModel::class)
    fun homeViewModel(viewModel: SymbolViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    fun infoViewModel(viewModel: InfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SymbolDetailsViewModel::class)
    fun symbolDetailsViewModel(viewModel: SymbolDetailsViewModel): ViewModel
}