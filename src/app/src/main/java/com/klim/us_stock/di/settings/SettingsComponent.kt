package com.klim.us_stock.di.settings

import com.klim.dep_in.ApplicationContextProvider
import com.klim.us_stock.ui.windows.settings.SettingsFragment
import dagger.Component

@SettingsScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [SettingsModule::class]
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