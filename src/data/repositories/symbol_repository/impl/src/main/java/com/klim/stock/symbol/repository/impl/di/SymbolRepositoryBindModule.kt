package com.klim.stock.symbol.repository.impl.di

import com.klim.stock.symbol.repository.api.SymbolRepository
import com.klim.stock.symbol.repository.impl.SymbolRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface SymbolRepositoryBindModule {

    @Binds
    fun bindSymbolRepository(repository: SymbolRepositoryImpl): SymbolRepository

}