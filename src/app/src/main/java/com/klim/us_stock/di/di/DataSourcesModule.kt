package com.klim.us_stock.di.di

import com.klim.network_api.apis.HistoryApi
import com.klim.network_api.apis.SearchStockSymbolApi
import com.klim.network_api.apis.StockSymbolApi
import com.klim.coreUi.data.repository.history.data_source.HistoryDataSourceI
import com.klim.coreUi.data.repository.history.data_source.remote.HistoryRemoteDataSource
import com.klim.sr.data_source.SymbolDataSourceI
import com.klim.sr.data_source.remote.SymbolRemoteDataSource
import com.klim.stock_repository.data_source.StockDataSourceI
import com.klim.stock_repository.data_source.remote.StockRemoteDataSource
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