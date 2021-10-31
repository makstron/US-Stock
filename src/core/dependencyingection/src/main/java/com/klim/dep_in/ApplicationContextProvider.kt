package com.klim.dep_in

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
//import com.klim.analytics.AnalyticsI

interface ApplicationContextProvider {

    fun getAppContext(): Context

    fun getApplication(): Application

//    fun getAppComponent(): AppComponent

    fun getViewModelFactory(): ViewModelProvider.Factory

//    fun getAnalytics(): AnalyticsI
}