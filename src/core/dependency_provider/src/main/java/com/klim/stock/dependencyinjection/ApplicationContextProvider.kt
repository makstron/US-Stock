package com.klim.stock.dependencyinjection

import android.app.Application
import android.content.Context

interface ApplicationContextProvider {

    fun getAppContext(): Context

    fun getApplication(): Application

}