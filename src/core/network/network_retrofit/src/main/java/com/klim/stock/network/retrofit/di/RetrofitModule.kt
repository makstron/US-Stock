package com.klim.stock.network.retrofit.di

import com.klim.stock.network.retrofit.RetrofitProvider
import com.klim.stock.network.api.HistoryApi
import com.klim.stock.network.api.SearchStockSymbolApi
import com.klim.stock.network.api.StockSymbolApi
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