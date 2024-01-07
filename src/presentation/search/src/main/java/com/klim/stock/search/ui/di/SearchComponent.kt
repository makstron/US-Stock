package com.klim.stock.search.ui.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ViewModelProviderProvider
import com.klim.stock.navigation.di.NavigationProvider
import com.klim.stock.search.ui.presentation.SearchFragment
import com.klim.stock.searchusecase.api.di.SearchUseCaseProvider
import com.klim.stock.utils.coroutines.di.CoroutineProvider
import dagger.Component

@SearchScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        ViewModelProviderProvider::class,
        CoroutineProvider::class,
        NavigationProvider::class,
        SearchUseCaseProvider::class,
    ],
    modules = [SearchModule::class, ViewModelsModule::class]
)
interface SearchComponent {
    fun inject(fragment: SearchFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                appComponent: ApplicationContextProvider,
                viewModelProvider: ViewModelProviderProvider,
                coroutineProvider: CoroutineProvider,
                navigationProvider: NavigationProvider,
                searchUseCaseProvider: SearchUseCaseProvider,
            ): SearchComponent =
                DaggerSearchComponent.builder()
                    .applicationContextProvider(appComponent)
                    .viewModelProviderProvider(viewModelProvider)
                    .coroutineProvider(coroutineProvider)
                    .navigationProvider(navigationProvider)
                    .searchUseCaseProvider(searchUseCaseProvider)
                    .build()
        }
    }
}