package com.klim.stock.favorited.usecase.api.entity

class FavoritedPreviewEntity(
    val symbol: String,
    val shortName: String,
    val regularPrice: Float,
    val regularMarketChange: Float,
    val regularMarketChangePercent: Float,
)