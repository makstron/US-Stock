package com.klim.smth.di

import com.klim.smth.data.repository.history.HistoryRepository
import com.klim.smth.data.repository.stock.StockRepository
import com.klim.smth.data.repository.symbol.SymbolRepository
import com.klim.smth.domain.repository.HistoryRepositoryI
import com.klim.smth.domain.repository.StockRepositoryI
import com.klim.smth.domain.repository.SymbolRepositoryI
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