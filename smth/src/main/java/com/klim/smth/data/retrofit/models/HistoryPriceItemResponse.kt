package com.klim.smth.data.retrofit.models

import com.google.gson.annotations.SerializedName

class HistoryPriceItemResponse(

    @SerializedName("t")
    val time: Long,

    @SerializedName("o")
    val open: Float,

    @SerializedName("c")
    val close: Float
)