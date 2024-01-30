package com.klim.stock.favorited.repository.impl.datasource

import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedPreviewDTO


interface FavoritedDataSource {

    suspend fun getFavorited(): List<FavoritedDTO>

    suspend fun getFavoritedPreview(): List<FavoritedPreviewDTO>

    suspend fun getFavoritedPreview(symbols: List<String>): List<FavoritedPreviewDTO>?

    suspend fun checkIsFavorited(symbol: String): Boolean

    suspend fun addFavorited(symbol: String)

    suspend fun addFavorited(symbol: FavoritedPreviewDTO)

    suspend fun removeFavorited(symbol: String)

}