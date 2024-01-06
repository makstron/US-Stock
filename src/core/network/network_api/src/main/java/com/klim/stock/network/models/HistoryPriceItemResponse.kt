package com.klim.stock.network.models

class HistoryPriceItemResponse(
    val time: Long,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
)