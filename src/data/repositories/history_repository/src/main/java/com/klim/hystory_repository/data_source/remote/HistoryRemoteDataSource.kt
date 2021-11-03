package com.klim.coreUi.data.repository.history.data_source.remote

import com.klim.coreUi.data.repository.history.data_source.HistoryDataSourceI
import com.klim.coreUi.data.repository.history.data_source.HistoryPriceDTO
import com.klim.coreUi.data.repository.history.map
import com.klim.network_api.apis.HistoryApi

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