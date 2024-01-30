package com.klim.stock.network.models.details

class SymbolDetailsResponse(
    val result: List<SymbolDetailsResult>?,
    val error: String? = null,
)