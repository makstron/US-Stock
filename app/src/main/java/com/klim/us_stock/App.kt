package com.klim.us_stock

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.klim.us_stock.di.AppComponent
import com.klim.us_stock.di.AppModule
import com.klim.us_stock.di.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}