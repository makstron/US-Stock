package com.klim.stock.favorited.repository.impl.datasource

import com.klim.stock.database.entity.FavouriteEntity
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedPreviewDTO


fun FavouriteEntity.mapFavoritedDTO(): FavoritedDTO = FavoritedDTO(
    symbol = this.symbol
)

fun FavouriteEntity.mapFavoritedPreviewDTO(): FavoritedPreviewDTO = FavoritedPreviewDTO(
    symbol = this.symbol,
    shortName = this.shortName ?: "",
    regularPrice = this.regularPrice ?: 0f,
    regularMarketChange = this.regularMarketChange ?: 0f,
    regularMarketChangePercent = this.regularMarketChangePercent ?: 0f,
    updatedTimestamp = this.updatedTimestamp,
)

fun FavoritedPreviewDTO.map(): FavouriteEntity = FavouriteEntity(
    symbol = this.symbol,
    shortName = this.shortName,
    regularPrice = this.regularPrice,
    regularMarketChange = this.regularMarketChange,
    regularMarketChangePercent = this.regularMarketChangePercent,
    updatedTimestamp = this.updatedTimestamp,
)