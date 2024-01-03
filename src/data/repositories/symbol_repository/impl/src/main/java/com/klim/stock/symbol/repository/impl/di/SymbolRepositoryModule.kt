package com.klim.stock.symbol.repository.impl.di

import dagger.Module

@Module (includes = [SymbolRepositoryBindModule::class, SymbolDataSourcesModule::class])
class SymbolRepositoryModule {

}