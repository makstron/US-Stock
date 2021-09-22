package com.klim.us_stock.domain.repository

import com.klim.us_stock.domain.entity.SymbolHistoryPriceEntity

interface HistoryRepositoryI {

    suspend fun getPricesForPeriod(symbol: String, olderDate: String, newerDate: String): List<SymbolHistoryPriceEntity>?

}