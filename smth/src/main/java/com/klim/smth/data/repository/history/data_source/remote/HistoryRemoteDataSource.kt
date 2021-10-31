package com.klim.smth.data.repository.history.data_source.remote

import com.klim.smth.data.repository.history.data_source.HistoryDataSourceI
import com.klim.smth.data.repository.history.data_source.HistoryPriceDTO
import com.klim.smth.data.repository.history.map
import com.klim.smth.data.retrofit.apis.HistoryApi

class HistoryRemoteDataSource(private val api: HistoryApi) : HistoryDataSourceI {

    override suspend fun getPeriodPrices(symbol: String, olderDate: String, newerDate: String): List<HistoryPriceDTO>? {
        try {
            val response = api.getPrice(symbol = symbol, olderDate = olderDate, newerDate = newerDate)
            if (response?.results != null) {
                return response.results.map {
                    it.map()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}