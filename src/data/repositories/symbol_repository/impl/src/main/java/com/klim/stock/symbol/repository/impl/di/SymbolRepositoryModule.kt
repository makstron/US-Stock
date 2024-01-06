package com.klim.stock.symbol.repository.impl.di

import com.klim.stock.symbol.repository.api.SymbolRepository
import com.klim.stock.symbol.repository.impl.SymbolRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [SymbolRepositoryBindModule::class, SymbolDataSourcesModule::class])
class SymbolRepositoryModule {}

@Module
interface SymbolRepositoryBindModule {

    @Binds
    fun bindSymbolRepository(repository: SymbolRepositoryImpl): SymbolRepository

}