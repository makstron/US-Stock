package com.klim.stock.favorited.repository.impl.di

import com.klim.stock.database.dao.FavouriteDAO
import com.klim.stock.dependencyinjection.LocalDataSource
import com.klim.stock.dependencyinjection.RemoteDataSource
import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.repository.impl.datasource.local.FavoritedLocalDataSource
import com.klim.stock.favorited.repository.impl.datasource.remote.FavoritedRemoteDataSource
import com.klim.stock.network.api.SymbolDetailsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritedDataSourcesModule {

    @Provides
    @Singleton
    @LocalDataSource
    fun provideFavoritedLocalDataSource(dao: FavouriteDAO): FavoritedDataSource {
        return FavoritedLocalDataSource(dao)
    }

    @Provides
    @Singleton
    @RemoteDataSource
    fun provideFavoritedRemoteDataSource(detailsApi: SymbolDetailsApi): FavoritedDataSource {
        return FavoritedRemoteDataSource(detailsApi)
    }
}