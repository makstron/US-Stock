package com.klim.stock.network.models.details

class SymbolDetailsResult(
    val symbol: String,
    val shortName: String,
    val longName: String,
    val currentPrice: Float,
    val marketChange: Float,
    val marketChangePercent: Float,
)