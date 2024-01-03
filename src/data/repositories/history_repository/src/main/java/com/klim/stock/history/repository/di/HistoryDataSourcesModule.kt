package com.klim.stock.history.repository.di

import com.klim.stock.history.repository.data_source.HistoryDataSourceI
import com.klim.stock.history.repository.data_source.remote.HistoryRemoteDataSource
import com.klim.stock.network.api.HistoryApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HistoryDataSourcesModule {

    @Provides
    @Singleton
    fun provideHistoryRemoteDataSource(api: HistoryApi): HistoryDataSourceI {
        return HistoryRemoteDataSource(api)
    }
}