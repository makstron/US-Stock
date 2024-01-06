package com.klim.stock.network.models.search

import com.google.gson.annotations.SerializedName

class SearchFinance(
    @SerializedName("result")
    val result: List<SearchResult>,
    val error: String,
)