package com.klim.stock.symbol.ui.di

import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ComponentScope
import com.klim.stock.favorited.usecase.api.di.FavoritedUseCaseProvider
import com.klim.stock.history.usecase.api.di.HistoryUseCaseProvider
import com.klim.stock.symbol.api.di.SymbolDetailsUseCaseProvider
import com.klim.stock.symbol.ui.presentation.SymbolDetailsFragment
import com.klim.stock.utils.coroutines.di.CoroutineProvider
import com.klim.stock.utils.geocoder.di.GeocoderProvider
import com.klim.stock.utils.phonenumber.di.PhoneNumberUtilsProvider
import dagger.Component

@ComponentScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        CoroutineProvider::class,
        GeocoderProvider::class,
        PhoneNumberUtilsProvider::class,
        AnalyticsProvider::class,
        SymbolDetailsUseCaseProvider::class,
        HistoryUseCaseProvider::class,
        FavoritedUseCaseProvider::class,
    ],
    modules = [ViewModelsModule::class]
)
interface SymbolDetailsComponent {

    fun inject(fragment: SymbolDetailsFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                appComponent: ApplicationContextProvider,
                coroutineProvider: CoroutineProvider,
                geocoderProvider: GeocoderProvider,
                analyticsProvider: AnalyticsProvider,
                phoneNumberUtilsProvider: PhoneNumberUtilsProvider,
                symbolDetailUCProvider: SymbolDetailsUseCaseProvider,
                historyUseCaseProvider: HistoryUseCaseProvider,
                favoritedUseCaseProvider: FavoritedUseCaseProvider,
            ): SymbolDetailsComponent =
                DaggerSymbolDetailsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .coroutineProvider(coroutineProvider)
                    .geocoderProvider(geocoderProvider)
                    .analyticsProvider(analyticsProvider)
                    .phoneNumberUtilsProvider(phoneNumberUtilsProvider)
                    .symbolDetailsUseCaseProvider(symbolDetailUCProvider)
                    .historyUseCaseProvider(historyUseCaseProvider)
                    .favoritedUseCaseProvider(favoritedUseCaseProvider)
                    .build()
        }
    }

}