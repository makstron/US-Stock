package com.klim.stock.favorited.ui.di

import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.favorited.ui.SymbolsFavoritedFragment
import com.klim.stock.favorited.usecase.api.di.FavoritedUseCaseProvider
import dagger.Component

@SymbolsScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        AnalyticsProvider::class,
        FavoritedUseCaseProvider::class,
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
                analyticsProvider: AnalyticsProvider,
                favoritedUseCaseProvider: FavoritedUseCaseProvider,
            ): SymbolsFavoritedComponent =
                DaggerSymbolsFavoritedComponent.builder()
                    .applicationContextProvider(appComponent)
                    .analyticsProvider(analyticsProvider)
                    .favoritedUseCaseProvider(favoritedUseCaseProvider)
                    .build()
        }
    }

}