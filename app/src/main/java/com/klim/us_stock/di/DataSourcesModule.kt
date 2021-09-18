package com.klim.us_stock.di

import com.klim.us_stock.data.repository.history.data_source.HistoryDataSourceI
import com.klim.us_stock.data.repository.history.data_source.remote.HistoryRemoteDataSource
import com.klim.us_stock.data.repository.stock.data_source.StockDataSourceI
import com.klim.us_stock.data.repository.stock.data_source.remote.StockRemoteDataSource
import com.klim.us_stock.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.us_stock.data.repository.symbol.data_source.remote.SymbolRemoteDataSource
import com.klim.us_stock.data.retrofit.apis.HistoryApi
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi
import com.klim.us_stock.data.retrofit.apis.StockSymbolApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourcesModule {

    @Provides
    @Singleton
    fun provideSymbolDataSource(api: SearchStockSymbolApi): SymbolDataSourceI {
        return SymbolRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideStockDataSource(api: StockSymbolApi): StockDataSourceI {
        return StockRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideHistoryRemoteDataSource(api: HistoryApi): HistoryDataSourceI {
        return HistoryRemoteDataSource(api)
    }
}