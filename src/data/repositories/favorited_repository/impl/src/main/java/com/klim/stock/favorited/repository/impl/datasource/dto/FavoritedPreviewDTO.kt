package com.klim.stock.favorited.repository.impl.datasource.dto

class FavoritedPreviewDTO(
    val symbol: String,
    val shortName: String,
    val regularPrice: Float,
    val regularMarketChange: Float,
    val regularMarketChangePercent: Float,
    val updatedTimestamp: Long,
)