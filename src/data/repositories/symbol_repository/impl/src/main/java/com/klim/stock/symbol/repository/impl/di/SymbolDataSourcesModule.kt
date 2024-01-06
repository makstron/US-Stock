package com.klim.stock.symbol.repository.impl.di

import com.klim.stock.network.api.SearchApi
import com.klim.stock.network.api.SymbolDetailsApi
import com.klim.stock.symbol.repository.impl.data_source.SymbolDataSourceI
import com.klim.stock.symbol.repository.impl.data_source.remote.SymbolRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SymbolDataSourcesModule {

    @Provides
    @Singleton
    fun provideSymbolDataSource(searchApi: SearchApi, detailsApi: SymbolDetailsApi): SymbolDataSourceI {
        return SymbolRemoteDataSource(searchApi, detailsApi)
    }
}