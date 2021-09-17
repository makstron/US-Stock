package com.klim.us_stock.domain.repository

import com.klim.us_stock.domain.entity.SymbolHistoryPriceEntity

interface HistoryRepositoryI {

    suspend fun getLastMonthPrices(symbol: String): List<SymbolHistoryPriceEntity>?

}