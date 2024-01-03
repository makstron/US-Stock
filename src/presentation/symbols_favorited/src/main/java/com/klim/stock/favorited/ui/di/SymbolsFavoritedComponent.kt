package com.klim.stock.favorited.ui.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.favorited.ui.SymbolsFavoritedFragment
import dagger.Component

@SymbolsScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        AnalyticsProvider::class,
    ],
    modules = [
        SymbolsFavoritedModule::class,
        ViewModelsBindModule::class,
    ]
)
interface SymbolsFavoritedComponent {

    fun inject(fragment: SymbolsFavoritedFragment)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, analyticsProvider: AnalyticsProvider): SymbolsFavoritedComponent =
                DaggerSymbolsFavoritedComponent.builder()
                    .applicationContextProvider(appComponent)
                    .analyticsProvider(analyticsProvider)
                    .build()
        }
    }

}