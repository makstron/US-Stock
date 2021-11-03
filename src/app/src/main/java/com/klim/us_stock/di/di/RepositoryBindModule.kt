package com.klim.us_stock.di.di

import com.klim.coreUi.data.repository.history.HistoryRepository
import com.klim.coreUi.domain.repository.HistoryRepositoryI
import com.klim.sr.SymbolRepository
import com.klim.sr.SymbolRepositoryI
import com.klim.stock_repository.StockRepository
import com.klim.stock_repository.StockRepositoryI
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