package com.klim.stock.favorited.repository.impl

import com.klim.stock.favorited.repository.api.FavoritedRepository
import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.usecase.api.entity.FavoritedEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritedRepositoryImpl
@Inject
constructor(
    private val favoritedDataSrc: FavoritedDataSource,
) : FavoritedRepository {

    override suspend fun getFavorited(): List<FavoritedEntity>? {
        return favoritedDataSrc.getFavorited()?.map { it.map() }
    }

    override suspend fun checkIsFavorited(symbol: String): Boolean {
        return favoritedDataSrc.checkIsFavorited(symbol)
    }

    override suspend fun setFavorited(symbol: String, isFavorited: Boolean) {
        favoritedDataSrc.setFavorited(symbol, isFavorited)
    }


}