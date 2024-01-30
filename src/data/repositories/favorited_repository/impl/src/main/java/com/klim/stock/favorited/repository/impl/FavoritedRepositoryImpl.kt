package com.klim.stock.favorited.repository.impl

import com.klim.stock.dependencyinjection.LocalDataSource
import com.klim.stock.dependencyinjection.RemoteDataSource
import com.klim.stock.favorited.repository.api.FavoritedRepository
import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.usecase.api.entity.FavoritedEntity
import com.klim.stock.favorited.usecase.api.entity.FavoritedPreviewEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritedRepositoryImpl
@Inject
constructor(
    @LocalDataSource
    private val favoritedLocalDataSrc: FavoritedDataSource,
    @RemoteDataSource
    private val favoritedRemoteDataSrc: FavoritedDataSource,
) : FavoritedRepository {

    override suspend fun getFavorited(): List<FavoritedEntity> {
        return favoritedLocalDataSrc.getFavorited().map { it.map() }
    }

    override suspend fun getFavoritedPreview(): List<FavoritedPreviewEntity> {
        val currentTime = System.currentTimeMillis()
        val localList = favoritedLocalDataSrc.getFavoritedPreview() //
        val resultList = mutableListOf<FavoritedPreviewEntity>()

        val groups = localList.groupBy { currentTime - it.updatedTimestamp > 12 * 60 * 60 * 1000 }
        val tooOldSymbols = groups[true]?.map { it.symbol } ?: emptyList()
        val freshEnough = groups[false]?.map { it.map() } ?: emptyList()
        resultList.addAll(freshEnough)

        if (tooOldSymbols.isNotEmpty()) {
            favoritedRemoteDataSrc.getFavoritedPreview(tooOldSymbols)
                ?.forEach {
                    favoritedLocalDataSrc.addFavorited(it)
                    resultList.add(it.map())
                }
        }

        return resultList
    }

    override suspend fun checkIsFavorited(symbol: String): Boolean {
        return favoritedLocalDataSrc.checkIsFavorited(symbol)
    }

    override suspend fun setFavorited(symbol: String, isFavorited: Boolean) {
        if (isFavorited) {
            favoritedLocalDataSrc.addFavorited(symbol)
        } else {
            favoritedLocalDataSrc.removeFavorited(symbol)
        }
    }


}