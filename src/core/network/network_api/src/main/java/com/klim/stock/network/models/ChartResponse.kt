package com.klim.stock.network.models

class ChartResponse(
    val results: List<ChartItemResponse>?,
    val error: Boolean? = false, //TODO: now extend it
)