package com.klim.stock.ui.di

import com.klim.stock.analytics.di.AnalyticsProvider
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.ui.MainActivity
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [
        ApplicationContextProvider::class,
        AnalyticsProvider::class
    ],
    modules = [ViewModelsModule::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, analyticsProvider: AnalyticsProvider): MainActivityComponent =
                DaggerMainActivityComponent.builder()
                    .applicationContextProvider(appComponent)
                    .analyticsProvider(analyticsProvider)
                    .build()
        }
    }
}