package com.klim.stock.di.info

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ViewModelProviderProvider
import com.klim.stock.ui.windows.info.InfoFragment
import dagger.Component

@InfoScope
@Component(
    dependencies = [ApplicationContextProvider::class, ViewModelProviderProvider::class],
    modules = [InfoModule::class]
)
interface InfoComponent {
    fun inject(fragment: InfoFragment?)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, viewModelProvider: ViewModelProviderProvider): InfoComponent =
                DaggerInfoComponent.builder()
                    .applicationContextProvider(appComponent)
                    .viewModelProviderProvider(viewModelProvider)
                    .build()
        }
    }
}