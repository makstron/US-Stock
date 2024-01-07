package com.klim.stock.symbol.ui.presentation.entity

class DetailsResultView(
    val symbol: String,
    val name: String,
    val sector: String,
    val industry: String,
    val ceo: String?,
    val employees: String,
    val address: String,
    val phone: String,
    val description: String,

    val recommendedSymbols: List<RecommendedEntityView>,
    val officers: List<OfficerEntityView>,
)