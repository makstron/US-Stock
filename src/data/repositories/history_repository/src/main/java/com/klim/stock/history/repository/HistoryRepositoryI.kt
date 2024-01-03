package com.klim.stock.history.repository

import com.klim.stock.symbol.api.entity.SymbolHistoryPriceEntity

interface HistoryRepositoryI {

    suspend fun getPricesForPeriod(symbol: String, olderDate: String, newerDate: String, forceUpdate: Boolean = false): List<SymbolHistoryPriceEntity>?

}