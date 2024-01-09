package com.klim.stock.favorited.repository.impl.datasource

import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO


interface FavoritedDataSource {

    suspend fun getFavorited(): List<FavoritedDTO>?

    suspend fun checkIsFavorited(symbol: String): Boolean

    suspend fun setFavorited(symbol: String, isFavorited: Boolean)

}