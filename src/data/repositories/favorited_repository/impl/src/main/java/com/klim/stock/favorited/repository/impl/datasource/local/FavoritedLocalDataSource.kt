package com.klim.stock.favorited.repository.impl.datasource.local

import com.klim.stock.database.dao.FavouriteDAO
import com.klim.stock.database.entity.FavouriteEntity
import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedPreviewDTO
import com.klim.stock.favorited.repository.impl.datasource.map
import com.klim.stock.favorited.repository.impl.datasource.mapFavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.mapFavoritedPreviewDTO

class FavoritedLocalDataSource(private val dao: FavouriteDAO) : FavoritedDataSource {

    override suspend fun getFavorited(): List<FavoritedDTO> {
        return dao.getAll().map { it.mapFavoritedDTO() }
    }

    override suspend fun getFavoritedPreview(): List<FavoritedPreviewDTO> {
        return dao.getAll().map { it.mapFavoritedPreviewDTO() }
    }

    override suspend fun getFavoritedPreview(symbols: List<String>): List<FavoritedPreviewDTO> {
        return dao.get(symbols).map { it.mapFavoritedPreviewDTO() }
    }

    override suspend fun checkIsFavorited(symbol: String): Boolean {
        return dao.get(symbol) != null
    }

    override suspend fun addFavorited(symbol: String) {
        dao.insert(FavouriteEntity(symbol))
    }

    override suspend fun addFavorited(symbol: FavoritedPreviewDTO) {
        dao.insert(symbol.map())
    }

    override suspend fun removeFavorited(symbol: String) {
        dao.delete(FavouriteEntity(symbol))
    }
}