package com.klim.stock.network.models

class HistoryPriceResponse(
    val results: List<HistoryPriceItemResponse>?,
    val error: Boolean? = false, //TODO: now extend it
)