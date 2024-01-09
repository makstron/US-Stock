package com.klim.stock.favorited.usecase.api.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.favorited.usecase.api.FavoritedUseCase

interface FavoritedUseCaseProvider : Dependency {
    val useCase: FavoritedUseCase
}