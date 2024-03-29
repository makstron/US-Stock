package com.klim.stock.settings.ui.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.settings.ui.presentation.SettingsFragment
import dagger.Component

@SettingsScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [ViewModelsModule::class]
)
interface SettingsComponent {
    fun inject(fragment: SettingsFragment?)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider): SettingsComponent =
                DaggerSettingsComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }
}