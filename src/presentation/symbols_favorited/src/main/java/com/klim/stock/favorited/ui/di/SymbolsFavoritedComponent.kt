package com.klim.stock.favorited.ui.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.dependencyinjection.ViewModelProviderProvider
import com.klim.stock.favorited.ui.SymbolsFavoritedFragment
import dagger.Component

@SymbolsScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        ViewModelProviderProvider::class,
        AnalyticsProvider::class,
    ],
    modules = [
        ViewModelsModule::class,
    ]
)
interface SymbolsFavoritedComponent {

    fun inject(fragment: SymbolsFavoritedFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                appComponent: ApplicationContextProvider,
                viewModelProvider: ViewModelProviderProvider,
                analyticsProvider: AnalyticsProvider
            ): SymbolsFavoritedComponent =
                DaggerSymbolsFavoritedComponent.builder()
                    .applicationContextProvider(appComponent)
                    .analyticsProvider(analyticsProvider)
                    .viewModelProviderProvider(viewModelProvider)
                    .build()
        }
    }

}