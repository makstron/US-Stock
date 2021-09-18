package com.klim.us_stock.di

import com.klim.us_stock.data.retrofit.RetrofitProvider
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi
import com.klim.us_stock.data.retrofit.apis.StockSymbolApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider.getRetrofit()
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

}