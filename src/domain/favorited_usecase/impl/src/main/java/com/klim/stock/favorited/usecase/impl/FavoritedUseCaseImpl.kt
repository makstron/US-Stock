package com.klim.stock.favorited.usecase.impl

import com.klim.stock.favorited.repository.api.FavoritedRepository
import com.klim.stock.favorited.usecase.api.FavoritedUseCase
import com.klim.stock.favorited.usecase.api.entity.FavoritedPreviewEntity
import javax.inject.Inject

class FavoritedUseCaseImpl
@Inject constructor(
    private val repositoryFavorited: FavoritedRepository,
) : FavoritedUseCase {

    override suspend fun getFavoritedPreviewList(): List<FavoritedPreviewEntity>? {
        return repositoryFavorited.getFavoritedPreview()
    }

    override suspend fun checkIsFavorited(symbol: String): Boolean {
        return repositoryFavorited.checkIsFavorited(symbol)
    }

    override suspend fun setFavorited(symbol: String, isFavorited: Boolean) {
        repositoryFavorited.setFavorited(symbol, isFavorited)
    }
}