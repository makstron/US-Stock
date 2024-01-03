package com.klim.stock.stock.repository.di

import com.klim.stock.network.api.StockSymbolApi
import com.klim.stock.stock.repository.data_source.StockDataSourceI
import com.klim.stock.stock.repository.data_source.remote.StockRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StockDataSourcesModule {

    @Provides
    @Singleton
    fun provideStockDataSource(api: StockSymbolApi): StockDataSourceI {
        return StockRemoteDataSource(api)
    }

}