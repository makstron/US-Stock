package com.klim.us_stock.di.info

import com.klim.di.ApplicationContextProvider
import com.klim.us_stock.ui.windows.info.InfoFragment
import dagger.Component

@InfoScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [InfoModule::class]
)
interface InfoComponent {
    fun inject(fragment: InfoFragment?)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider): InfoComponent =
                DaggerInfoComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }
}