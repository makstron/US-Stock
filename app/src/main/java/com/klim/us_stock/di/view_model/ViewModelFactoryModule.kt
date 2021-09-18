package com.klim.us_stock.di.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klim.us_stock.ui.windows.home.HomeViewModel
import com.klim.us_stock.ui.windows.info.InfoViewModel
import com.klim.us_stock.ui.windows.search.SearchViewModel
import com.klim.us_stock.ui.windows.settings.SettingsViewModel
import com.klim.us_stock.ui.windows.symbol_details.SymbolDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    internal abstract fun infoViewModel(viewModel: InfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SymbolDetailsViewModel::class)
    internal abstract fun symbolDetailsViewModel(viewModel: SymbolDetailsViewModel): ViewModel
}