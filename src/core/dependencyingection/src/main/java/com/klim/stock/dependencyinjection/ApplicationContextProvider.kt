package com.klim.stock.dependencyinjection

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider

interface ApplicationContextProvider {

    fun getAppContext(): Context

    fun getApplication(): Application

}

interface ViewModelProviderProvider {

    fun getViewModelFactory(): ViewModelProvider.Factory

}