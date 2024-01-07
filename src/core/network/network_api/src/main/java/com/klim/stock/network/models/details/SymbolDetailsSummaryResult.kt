package com.klim.stock.network.models.details

class SymbolDetailsSummaryResult(
    val sector: String,
    val industry: String,
    val employees: Int,
    val hqAddress: String,
    val phone: String,
    val description: String,
    val officers: List<SymbolOfficer>,
)