package com.klim.us_stock

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.klim.analytics.AnalyticsI
import com.klim.di.ApplicationContextProvider
import com.klim.us_stock.di.ComponentsProvider


class App : Application(), ApplicationContextProvider {

    lateinit var componentsProvider: ComponentsProvider

    override fun onCreate() {
        super.onCreate()
        componentsProvider = ComponentsProvider(this)
        componentsProvider.appComponent.inject(this)

//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
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
        return componentsProvider.appComponent.getViewModelFactory()
    }

    override fun getAnalytics(): AnalyticsI {
        return componentsProvider.appComponent.getAnalytics()
    }


//    override fun getAppComponent(): AppComponent {
//        return componentsProvider.appComponent
//    }

}