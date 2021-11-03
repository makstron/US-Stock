package com.klim.coreUi.domain.repository

import com.klim.coreUi.domain.entity.SymbolHistoryPriceEntity

interface HistoryRepositoryI {

    suspend fun getPricesForPeriod(symbol: String, olderDate: String, newerDate: String, forceUpdate: Boolean = false): List<SymbolHistoryPriceEntity>?

}