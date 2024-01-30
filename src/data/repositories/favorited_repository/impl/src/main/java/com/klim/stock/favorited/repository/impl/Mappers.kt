package com.klim.stock.favorited.repository.impl

import com.klim.stock.database.entity.FavouriteEntity
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedPreviewDTO
import com.klim.stock.favorited.usecase.api.entity.FavoritedEntity
import com.klim.stock.favorited.usecase.api.entity.FavoritedPreviewEntity
import com.klim.stock.network.models.details.SymbolDetailsResponse
import com.klim.stock.network.models.details.SymbolDetailsResult


fun FavoritedDTO.map(): FavoritedEntity = FavoritedEntity(
    this.symbol
)

fun FavoritedPreviewDTO.map(): FavoritedPreviewEntity = FavoritedPreviewEntity(
    symbol = this.symbol,
    shortName = this.shortName,
    regularPrice = this.regularPrice,
    regularMarketChange = this.regularMarketChange,
    regularMarketChangePercent = this.regularMarketChangePercent,
)

fun SymbolDetailsResult.map(): FavoritedPreviewDTO = FavoritedPreviewDTO(
    symbol = this.symbol,
    shortName = this.shortName,
    regularPrice = this.currentPrice,
    regularMarketChange = this.marketChange,
    regularMarketChangePercent = this.marketChangePercent,
    updatedTimestamp = System.currentTimeMillis(),
)