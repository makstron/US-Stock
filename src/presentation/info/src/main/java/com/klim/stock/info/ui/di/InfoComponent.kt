package com.klim.stock.info.ui.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.info.ui.presentation.InfoFragment
import dagger.Component

@InfoScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [ViewModelsModule::class]
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