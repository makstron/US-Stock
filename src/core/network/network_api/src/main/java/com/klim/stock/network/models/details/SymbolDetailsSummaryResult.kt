package com.klim.stock.network.models.details

class SymbolDetailsSummaryResult(
    val sector: String,
    val industry: String,
    val ceo: String,
    val employees: Int,
    val hq_address: String,
    val phone: String,
    val description: String,
    val tags: List<String>,
    val similar: List<String>,
)