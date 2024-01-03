package com.klim.stock.stock.repository.di

import com.klim.stock.stock.repository.StockRepository
import com.klim.stock.stock.repository.StockRepositoryI
import dagger.Binds
import dagger.Module

@Module
interface StockRepositoryBindModule {

    @Binds
    fun bindStockRepositoryI(repository: StockRepository): StockRepositoryI

}