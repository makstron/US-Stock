package com.klim.network_retrofit.di

import com.klim.network_retrofit.RetrofitProvider
import com.klim.network_api.apis.HistoryApi
import com.klim.network_api.apis.SearchStockSymbolApi
import com.klim.network_api.apis.StockSymbolApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
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