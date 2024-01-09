package com.klim.stock.favorited.repository.impl.di

import com.klim.stock.database.dao.FavoritedDAO
import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.repository.impl.datasource.local.FavoritedLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritedDataSourcesModule {

    @Provides
    @Singleton
    fun provideFavoritedRemoteDataSource(dao: FavoritedDAO): FavoritedDataSource {
        return FavoritedLocalDataSource(dao)
    }
}