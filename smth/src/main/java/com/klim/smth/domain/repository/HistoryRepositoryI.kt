package com.klim.smth.domain.repository

import com.klim.smth.domain.entity.SymbolHistoryPriceEntity

interface HistoryRepositoryI {

    suspend fun getPricesForPeriod(symbol: String, olderDate: String, newerDate: String, forceUpdate: Boolean = false): List<SymbolHistoryPriceEntity>?

}