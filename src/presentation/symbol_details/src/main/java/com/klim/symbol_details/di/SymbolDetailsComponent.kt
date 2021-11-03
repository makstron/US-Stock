package com.klim.symbol_details.di

import com.klim.coreUi.BaseFragment
import com.klim.dep_in.ApplicationContextProvider
import com.klim.symbol_details.presentation.SymbolDetailsFragment
import dagger.Component

@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [SymbolDetailsModule::class, ViewModelsBindModule::class]
)
interface SymbolDetailsComponent {

    fun inject(fragment: SymbolDetailsFragment)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, bf: BaseFragment): SymbolDetailsComponent =
                DaggerSymbolDetailsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }

}