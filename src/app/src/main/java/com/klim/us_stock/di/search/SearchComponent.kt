package com.klim.us_stock.di.search

import com.klim.dep_in.ApplicationContextProvider
import com.klim.us_stock.ui.windows.search.SearchFragment
import dagger.Component

@SearchScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [SearchModule::class]
)
interface SearchComponent {
    fun inject(fragment: SearchFragment?)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider): SearchComponent =
                DaggerSearchComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }
}