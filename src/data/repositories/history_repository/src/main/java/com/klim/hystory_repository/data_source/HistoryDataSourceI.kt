package com.klim.coreUi.data.repository.history.data_source

interface HistoryDataSourceI {

    suspend fun getPeriodPrices(symbol: String, olderDate: String, newerDate: String): List<HistoryPriceDTO>?
}