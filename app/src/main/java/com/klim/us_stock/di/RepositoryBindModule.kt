package com.klim.us_stock.di

import com.klim.us_stock.data.repository.history.HistoryRepository
import com.klim.us_stock.data.repository.stock.StockRepository
import com.klim.us_stock.data.repository.symbol.SymbolRepository
import com.klim.us_stock.domain.repository.HistoryRepositoryI
import com.klim.us_stock.domain.repository.StockRepositoryI
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import dagger.Binds
import dagger.Module

@Module
interface RepositoryBindModule {

    @Binds
    fun bindStockRepositoryI(repository: StockRepository): StockRepositoryI

    @Binds
    fun bindSymbolRepository(repository: SymbolRepository): SymbolRepositoryI

    @Binds
    fun bindHistoryRepository(repository: HistoryRepository): HistoryRepositoryI

}