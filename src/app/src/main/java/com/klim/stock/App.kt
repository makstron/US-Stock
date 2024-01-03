package com.klim.stock

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.network.retrofit.di.DaggerNetworkComponent
import com.klim.stock.dicore.DependenciesMap
import com.klim.stock.dicore.DependencyContainer
import com.klim.stock.di.AppComponent
import com.klim.stock.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), ApplicationContextProvider, DependencyContainer {

    @Inject
    override lateinit var dependenciesMap: DependenciesMap

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val networkComponent = DaggerNetworkComponent.builder()
            .build()

        appComponent = DaggerAppComponent
            .builder()
            .app(this)
            .apiProvider(networkComponent)
            .build()

        appComponent.inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getAppContext(): Context {
        return this
    }

    override fun getApplication(): Application {
        return this
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return appComponent.getViewModelFactory()
    }

}