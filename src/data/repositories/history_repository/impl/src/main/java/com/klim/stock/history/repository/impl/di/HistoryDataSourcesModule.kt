package com.klim.stock.history.repository.impl.di

import com.klim.stock.history.repository.impl.data_source.HistoryDataSource
import com.klim.stock.history.repository.impl.data_source.remote.HistoryRemoteDataSource
import com.klim.stock.network.api.HistoryApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HistoryDataSourcesModule {

    @Provides
    @Singleton
    fun provideHistoryRemoteDataSource(api: HistoryApi): HistoryDataSource {
        return HistoryRemoteDataSource(api)
    }
}