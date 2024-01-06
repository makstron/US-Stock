package com.klim.stock.di.main_activity

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.ViewModelProviderProvider
import com.klim.stock.ui.MainActivity
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [ApplicationContextProvider::class, ViewModelProviderProvider::class],
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider, viewModelProvider: ViewModelProviderProvider): MainActivityComponent =
                DaggerMainActivityComponent.builder()
                    .applicationContextProvider(appComponent)
                    .viewModelProviderProvider(viewModelProvider)
                    .build()
        }
    }
}