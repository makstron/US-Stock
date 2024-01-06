package com.klim.stock.history.repository.impl.data_source.remote

import com.klim.stock.history.repository.impl.data_source.HistoryDataSource
import com.klim.stock.history.repository.impl.data_source.dto.HistoryPriceDTO
import com.klim.stock.history.repository.impl.map
import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.network.api.HistoryApi

class HistoryRemoteDataSource(private val api: HistoryApi) : HistoryDataSource {

    override suspend fun getSymbolPricesHistory(params: HistoryUseCase.RequestParams): List<HistoryPriceDTO>? {
        try {
            val response = api.getHistoryPrices(symbol = params.symbol, interval = params.timeInterval.map(), range = params.range.map())
            response.results?.let { results ->
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