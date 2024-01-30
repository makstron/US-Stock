package com.klim.stock.favorited.ui.di

import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.favorited.ui.SymbolFavoritedViewModelImpl
import com.klim.stock.favorited.ui.SymbolsFavoritedFragment
import com.klim.stock.favorited.usecase.api.di.FavoritedUseCaseProvider
import com.klim.stock.navigation.di.NavigationProvider
import dagger.Component

@SymbolsScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        NavigationProvider::class,
        AnalyticsProvider::class,
        FavoritedUseCaseProvider::class,
    ],
    modules = [
        ViewModelsModule::class,
    ]
)
interface SymbolsFavoritedComponent {

    fun inject(fragment: SymbolsFavoritedFragment)

     fun getViewModel() : SymbolFavoritedViewModelImpl

    class Initializer private constructor() {
        companion object {
            fun init(
                appComponent: ApplicationContextProvider,
                navigationProvider: NavigationProvider,
                analyticsProvider: AnalyticsProvider,
                favoritedUseCaseProvider: FavoritedUseCaseProvider,
            ): SymbolsFavoritedComponent =
                DaggerSymbolsFavoritedComponent.builder()
                    .applicationContextProvider(appComponent)
                    .navigationProvider(navigationProvider)
                    .analyticsProvider(analyticsProvider)
                    .favoritedUseCaseProvider(favoritedUseCaseProvider)
                    .build()
        }
    }

}