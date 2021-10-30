package com.klim.us_stock.di

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
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider.retrofit
    }

    @Provides
    @Singleton
    @DateFormatRequest
    fun provideRequestDateFormatter(@TimezoneServer timeZone: TimeZone): SimpleDateFormat {
        return SimpleDateFormat(RetrofitProvider.REQUEST_DATE_FORMAT).apply {
            this.timeZone = timeZone
        }
    }

    @Provides
    @Singleton
    @TimezoneServer
    fun provideServerTimezone(): TimeZone {
        return TimeZone.getTimeZone(RetrofitProvider.SERVER_TIMEZONE)
    }

    @Provides
    @Singleton
    fun provideSearchStockSymbolApi(retrofit: Retrofit): SearchStockSymbolApi {
        return retrofit.create<SearchStockSymbolApi>(SearchStockSymbolApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockSymbolApi(retrofit: Retrofit): StockSymbolApi {
        return retrofit.create<StockSymbolApi>(StockSymbolApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHistoryApi(retrofit: Retrofit): HistoryApi {
        return retrofit.create<HistoryApi>(HistoryApi::class.java)
    }

}