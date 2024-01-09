package com.klim.stock.favorited.repository.impl.di

import com.klim.stock.favorited.repository.api.FavoritedRepository
import com.klim.stock.favorited.repository.impl.FavoritedRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [FavoritedRepositoryBindModule::class, FavoritedDataSourcesModule::class])
class FavoritedRepositoryModule {

}

@Module
interface FavoritedRepositoryBindModule {

    @Binds
    fun bindFavoritedRepository(repository: FavoritedRepositoryImpl): FavoritedRepository

}