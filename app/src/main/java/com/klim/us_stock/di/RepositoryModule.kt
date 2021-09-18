package com.klim.us_stock.di

import com.klim.us_stock.data.repository.stock.StockRepository
import com.klim.us_stock.data.repository.stock.data_source.StockDataSourceI
import com.klim.us_stock.data.repository.symbol.SymbolRepository
import com.klim.us_stock.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.us_stock.domain.repository.StockRepositoryI
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStockRepository(stockDataSourceRemote: StockDataSourceI): StockRepositoryI {
        return StockRepository(stockDataSourceRemote)
    }

    @Provides
    @Singleton
    fun provideSymbolRepository(symbolDataSourceRemote: SymbolDataSourceI): SymbolRepositoryI {
        return SymbolRepository(symbolDataSourceRemote)
    }

}