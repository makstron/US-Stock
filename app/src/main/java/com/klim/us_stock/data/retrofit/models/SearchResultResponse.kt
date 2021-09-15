package com.klim.us_stock.data.retrofit.models

class SearchResultResponse(
    val results: List<SearchResultItem>?,
    val status: String,
    val next_url: String?,
    val count: Int?,
)