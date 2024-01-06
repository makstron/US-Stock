package com.klim.stock.network.models.search

import com.google.gson.annotations.SerializedName

class SearchResult(

    @SerializedName("start")
    val startIdx: Int?,

    @SerializedName("count")
    val countRequested: Int?,

    @SerializedName("total")
    val countReceived: Int?,

    @SerializedName("documents")
    val results: List<SearchDocument>?,

    )