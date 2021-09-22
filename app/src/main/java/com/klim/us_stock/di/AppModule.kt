package com.klim.us_stock.di

import com.klim.us_stock.App
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApp(): App {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }
}