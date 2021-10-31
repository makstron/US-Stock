package com.klim.symbol_details.di

import com.klim.dep_in.ApplicationContextProvider
import com.klim.symbol_details.presentation.SymbolDetailsFragment
import com.klim.us_stock.di.symbol_details.SymbolDetailsModule
import dagger.Component

@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [SymbolDetailsModule::class]
)
interface SymbolDetailsComponent {

    fun inject(fragment: SymbolDetailsFragment)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider): SymbolDetailsComponent =
                DaggerSymbolDetailsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }

}