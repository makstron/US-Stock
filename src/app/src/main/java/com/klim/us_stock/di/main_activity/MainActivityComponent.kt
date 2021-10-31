package com.klim.us_stock.di.main_activity

import com.klim.di.ApplicationContextProvider
import com.klim.us_stock.ui.MainActivity
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [ApplicationContextProvider::class],
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    class Initializer private constructor() {
        companion object {
            fun init(appComponent: ApplicationContextProvider): MainActivityComponent =
                DaggerMainActivityComponent.builder()
                    .applicationContextProvider(appComponent)
                    .build()
        }
    }
}