package com.klim.stock.network.models

class HistoryPriceResponse(
    val results: List<HistoryPriceItemResponse>?,
    val count: Int
)