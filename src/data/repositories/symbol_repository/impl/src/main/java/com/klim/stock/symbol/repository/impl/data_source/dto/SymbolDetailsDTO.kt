package com.klim.stock.symbol.repository.impl.data_source.dto

class SymbolDetailsDTO(
    val symbol: String,
    val name: String,
    val sector: String,
    val industry: String,
    val employees: Int,
    val address: String,
    val phone: String,
    val description: String,
    val officers: List<OfficerDTO>,
    val recommendedSymbols: List<RecommendedSymbolDTO>,
    val currentPrice: Float,
    val marketChange: Float,
    val marketChangePercent: Float,
)