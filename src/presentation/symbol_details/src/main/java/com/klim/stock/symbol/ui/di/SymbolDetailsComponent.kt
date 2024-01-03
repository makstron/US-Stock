package com.klim.stock.symbol.ui.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ComponentScope
import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.symbol.api.di.SymbolDetailsUseCaseProvider
import com.klim.stock.symbol.ui.presentation.SymbolDetailsFragment
import com.klim.stock.utils.geocoder.di.GeocoderProvider
import com.klim.stock.utils.phonenumber.di.PhoneNumberUtilsProvider
import dagger.Component

@ComponentScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        GeocoderProvider::class,
        PhoneNumberUtilsProvider::class,
        AnalyticsProvider::class,
        SymbolDetailsUseCaseProvider::class,
    ],
    modules = [SymbolDetailsModule::class, ViewModelsBindModule::class]
)
interface SymbolDetailsComponent {

    fun inject(fragment: SymbolDetailsFragment)

    class Initializer private constructor() {
        companion object {
            fun init(
                appComponent: ApplicationContextProvider,
                geocoderProvider: GeocoderProvider,
                analyticsProvider: AnalyticsProvider,
                phoneNumberUtilsProvider: PhoneNumberUtilsProvider,
                symbolDetailUCProvider: SymbolDetailsUseCaseProvider
            ): SymbolDetailsComponent =
                DaggerSymbolDetailsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .geocoderProvider(geocoderProvider)
                    .analyticsProvider(analyticsProvider)
                    .phoneNumberUtilsProvider(phoneNumberUtilsProvider)
                    .symbolDetailsUseCaseProvider(symbolDetailUCProvider)
                    .build()
        }
    }

}