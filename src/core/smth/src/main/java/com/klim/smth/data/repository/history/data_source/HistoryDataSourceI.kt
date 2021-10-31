package com.klim.smth.data.repository.history.data_source

interface HistoryDataSourceI {

    suspend fun getPeriodPrices(symbol: String, olderDate: String, newerDate: String): List<HistoryPriceDTO>?
}