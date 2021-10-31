package com.klim.us_stock.di.home

import com.klim.dep_in.ApplicationContextProvider
import com.klim.symbol_details.di.DaggerSymbolDetailsComponent
import com.klim.symbol_details.di.SymbolDetailsComponent
import com.klim.us_stock.di.main_activity.MainActivityModule
import com.klim.us_stock.ui.windows.home.SymbolsFragment
import dagger.Component

@SymbolsScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [MainActivityModule::class]
)
interface SymbolsComponent {

    fun inject(fragment: SymbolsFragment)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider): SymbolsComponent =
                DaggerSymbolsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }

}