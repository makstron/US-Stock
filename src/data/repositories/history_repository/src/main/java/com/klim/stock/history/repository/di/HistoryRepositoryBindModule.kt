package com.klim.stock.history.repository.di

import com.klim.stock.history.repository.HistoryRepository
import com.klim.stock.history.repository.HistoryRepositoryI
import dagger.Binds
import dagger.Module

@Module
interface HistoryRepositoryBindModule {

    @Binds
    fun bindHistoryRepository(repository: HistoryRepository): HistoryRepositoryI

}