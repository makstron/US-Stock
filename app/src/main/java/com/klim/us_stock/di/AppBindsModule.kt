package com.klim.us_stock.di

import android.app.Application
import android.content.Context
import com.klim.us_stock.App
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {

    @Binds
    fun provideContext(app: App): Context

    @Binds
    fun provideApplication(app: App): Application

}