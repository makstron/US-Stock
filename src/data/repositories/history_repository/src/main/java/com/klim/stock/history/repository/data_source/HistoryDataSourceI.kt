package com.klim.stock.history.repository.data_source

interface HistoryDataSourceI {

    suspend fun getPeriodPrices(symbol: String, olderDate: String, newerDate: String): List<HistoryPriceDTO>?
}