package com.klim.stock.history.repository.impl.di

import com.klim.stock.history.repository.impl.HistoryRepositoryImpl
import com.klim.stock.history.repository.api.HistoryRepository
import dagger.Binds
import dagger.Module

@Module(includes = [HistoryRepositoryBindModule::class, HistoryDataSourcesModule::class])
class HistoryRepositoryModule {

}

@Module
interface HistoryRepositoryBindModule {

    @Binds
    fun bindHistoryRepository(repository: HistoryRepositoryImpl): HistoryRepository

}