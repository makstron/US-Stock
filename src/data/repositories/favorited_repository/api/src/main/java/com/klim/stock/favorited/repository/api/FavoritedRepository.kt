package com.klim.stock.favorited.repository.api

import com.klim.stock.favorited.usecase.api.entity.FavoritedEntity
import com.klim.stock.favorited.usecase.api.entity.FavoritedPreviewEntity


interface FavoritedRepository {

    suspend fun getFavorited(): List<FavoritedEntity>

    suspend fun getFavoritedPreview(): List<FavoritedPreviewEntity>

    suspend fun checkIsFavorited(symbol: String): Boolean

    suspend fun setFavorited(symbol: String, isFavorited: Boolean)

}