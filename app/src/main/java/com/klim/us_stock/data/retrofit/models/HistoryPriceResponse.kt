package com.klim.us_stock.data.retrofit.models

class HistoryPriceResponse(
    val results: List<HistoryPriceItemResponse>?,
    val count: Int
)