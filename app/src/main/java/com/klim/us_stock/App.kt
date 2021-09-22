package com.klim.us_stock

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.klim.us_stock.di.ComponentsProvider


class App : Application() {

    lateinit var componentsProvider: ComponentsProvider

    override fun onCreate() {
        super.onCreate()
        componentsProvider = ComponentsProvider(this)
        componentsProvider.appComponent.inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}