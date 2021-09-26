package com.klim.us_stock.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.klim.us_stock.data.retrofit.RetrofitProvider
import com.klim.us_stock.data.retrofit.apis.HistoryApi
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi
import com.klim.us_stock.data.retrofit.apis.StockSymbolApi
import com.klim.us_stock.di.qualifiers.DateFormatRequest
import com.klim.us_stock.di.qualifiers.TimezoneServer
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

}