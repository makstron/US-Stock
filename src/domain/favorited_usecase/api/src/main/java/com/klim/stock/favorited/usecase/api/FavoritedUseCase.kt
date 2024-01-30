package com.klim.stock.favorited.usecase.api

import com.klim.stock.favorited.usecase.api.entity.FavoritedPreviewEntity

interface FavoritedUseCase {

    suspend fun getFavoritedPreviewList(): List<FavoritedPreviewEntity>?

    suspend fun checkIsFavorited(symbol: String): Boolean

    suspend fun setFavorited(symbol: String, isFavorited: Boolean)

}

