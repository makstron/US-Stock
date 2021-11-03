package com.klim.sr.data_source.dto

class SymbolDetailsDTO(
    val symbol: String,
    val name: String,
    val sector: String,
    val industry: String,
    val ceo: String,
    val employees: Int,
    val address: String,
    val phone: String,
    val description: String,
    val tags: List<TagDTO>,
    val relatedStocks: List<RelatedStockDTO>,
)