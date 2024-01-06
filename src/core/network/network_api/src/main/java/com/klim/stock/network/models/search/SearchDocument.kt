package com.klim.stock.network.models.search

import com.google.gson.annotations.SerializedName

class SearchDocument(

    val symbol: String,

    @SerializedName("shortName")
    val name: String?,

    )