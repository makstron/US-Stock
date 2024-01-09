package com.klim.stock.favorited.repository.impl.datasource.local

import com.klim.stock.database.dao.FavoritedDAO
import com.klim.stock.database.entity.FavoritedEntity
import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.map

class FavoritedLocalDataSource(private val dao: FavoritedDAO) : FavoritedDataSource {

    override suspend fun getFavorited(): List<FavoritedDTO>? {
        return dao.getAll()?.map { it.map() }
    }

    override suspend fun checkIsFavorited(symbol: String): Boolean {
        return dao.get(symbol) != null
    }

    override suspend fun setFavorited(symbol: String, isFavorited: Boolean) {
        if (isFavorited) {
            dao.insert(FavoritedEntity(symbol))
        } else {
            dao.delete(FavoritedEntity(symbol))
        }
    }

}