package com.klim.stock.network.models.search

import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("finance")
    val finance: SearchFinance,
)