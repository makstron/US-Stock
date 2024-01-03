package com.klim.stock.symbol.api

import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.api.entity.SymbolHistoryPriceEntity
import com.klim.stock.symbol.api.entity.SymbolPriceSummaryEntity

interface SymbolDetailsUseCase {

    class RequestParams(val symbol: String)

    suspend fun getDetails(params: RequestParams): SymbolDetailsEntity?

    suspend fun getPrice(params: RequestParams): SymbolPriceSummaryEntity?

    suspend fun getLastMonthPrice(params: RequestParams): List<SymbolHistoryPriceEntity>?

}

