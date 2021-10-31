package com.klim.smth.data.repository.history.data_source.remote

import com.klim.network_api.apis.HistoryApi
import com.klim.smth.data.repository.history.data_source.HistoryDataSourceI
import com.klim.smth.data.repository.history.data_source.HistoryPriceDTO
import com.klim.smth.data.repository.history.map

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