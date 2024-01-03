package com.klim.stock.symbol.api.entity

public class SymbolPriceSummaryEntity(
    val currentPrice: Float,
    public val valueFromLatest: Float?,
    val percentFromLatest: Float?,
)