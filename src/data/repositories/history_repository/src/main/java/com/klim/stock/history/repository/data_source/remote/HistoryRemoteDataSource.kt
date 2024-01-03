package com.klim.stock.history.repository.data_source.remote

import com.klim.stock.history.repository.data_source.HistoryDataSourceI
import com.klim.stock.history.repository.data_source.HistoryPriceDTO
import com.klim.stock.history.repository.map
import com.klim.stock.network.api.HistoryApi

class HistoryRemoteDataSource(private val api: HistoryApi) : HistoryDataSourceI {

    override suspend fun getPeriodPrices(symbol: String, olderDate: String, newerDate: String): List<HistoryPriceDTO>? {
        try {
            val response = api.getPrice(symbol = symbol, olderDate = olderDate, newerDate = newerDate)
            response?.results?.let { results ->
                return results.map {
                    it.map()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}