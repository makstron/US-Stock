package com.klim.us_stock.data.retrofit.models

import com.google.gson.annotations.SerializedName

class HistoryPriceItemResponse(

    @SerializedName("t")
    val time: Long,

    @SerializedName("o")
    val open: Float,

    @SerializedName("c")
    val close: Float
)