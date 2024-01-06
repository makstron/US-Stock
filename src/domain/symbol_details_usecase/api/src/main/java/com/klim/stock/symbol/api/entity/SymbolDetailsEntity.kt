package com.klim.stock.symbol.api.entity

class SymbolDetailsEntity(
    val symbol: String,
    val name: String,
    val sector: String,
    val industry: String,
    val ceo: String,
    val employees: Int,
    val address: String,
    val phone: String,
    val description: String,
    val tags: List<TagEntity>,
    val relatedStocks: List<RelatedStockEntity>,
    val currentPrice: Float,
    val marketChange: Float,
    val marketChangePercent: Float,
)