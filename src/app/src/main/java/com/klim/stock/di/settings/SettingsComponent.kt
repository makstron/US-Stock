package com.klim.stock.di.settings

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ViewModelProviderProvider
import com.klim.stock.ui.windows.settings.SettingsFragment
import dagger.Component

@SettingsScope
@Component(
    dependencies = [ApplicationContextProvider::class, ViewModelProviderProvider::class],
    modules = [SettingsModule::class]
)
interface SettingsComponent {
    fun inject(fragment: SettingsFragment?)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, viewModelProvider: ViewModelProviderProvider): SettingsComponent =
                DaggerSettingsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .viewModelProviderProvider(viewModelProvider)
                    .build()
        }
    }
}