package com.klim.us_stock

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.klim.analytics.AnalyticsI
import com.klim.analytics.di.DaggerAnalyticsComponent
import com.klim.constants.di.DaggerConstantsComponent
import com.klim.dep_in.ApplicationContextProvider
import com.klim.network_retrofit.di.DaggerNetworkComponent
import com.klim.us_stock.di.AppComponent
import com.klim.us_stock.di.ComponentsProvider
import com.klim.us_stock.di.DaggerAppComponent


class App : Application(), ApplicationContextProvider {

//    lateinit var componentsProvider: ComponentsProvider

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
//        componentsProvider = ComponentsProvider(this)
//        componentsProvider.appComponent.inject(this)


//        val appComponent: AppComponent
//        get

//        init {

            var const = DaggerConstantsComponent.builder()
                .build()

            var nc = DaggerNetworkComponent.builder()
                .build()

            var analytics = DaggerAnalyticsComponent.builder()
                .context(this)
                .build()

            appComponent = DaggerAppComponent
                .builder()
                .app(this)
                .apiProvider(nc)
                .constantsProvider(const)
                .analyticsDependency(analytics)
                .build()
//        }

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
        return appComponent.getViewModelFactory()
    }

//    override fun getAnalytics(): AnalyticsI {
//        return appComponent.getAnalytics()
//    }


//    override fun getAppComponent(): AppComponent {
//        return componentsProvider.appComponent
//    }

}