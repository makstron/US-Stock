package com.klim.stock.di.search

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ViewModelProviderProvider
import com.klim.stock.ui.windows.search.SearchFragment
import dagger.Component

@SearchScope
@Component(
    dependencies = [ApplicationContextProvider::class, ViewModelProviderProvider::class],
    modules = [SearchModule::class]
)
interface SearchComponent {
    fun inject(fragment: SearchFragment?)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, viewModelProvider: ViewModelProviderProvider): SearchComponent =
                DaggerSearchComponent.builder()
                    .applicationContextProvider(appComponent)
                    .viewModelProviderProvider(viewModelProvider)
                    .build()
        }
    }
}