package com.klim.us_stock.di

import com.klim.us_stock.data.repository.stock.StockRepository
import com.klim.us_stock.data.repository.stock.data_source.StockDataSourceI
import com.klim.us_stock.data.repository.stock.data_source.remote.StockRemoteDataSource
import com.klim.us_stock.data.repository.symbol.SymbolRepository
import com.klim.us_stock.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.us_stock.data.repository.symbol.data_source.remote.SymbolRemoteDataSource
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi
import com.klim.us_stock.data.retrofit.apis.StockSymbolApi
import com.klim.us_stock.domain.repository.StockRepositoryI
import com.klim.us_stock.domain.repository.SymbolRepositoryI
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

}