package com.klim.stock.network.models

class ChartItemResponse(
    val time: Long,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
)