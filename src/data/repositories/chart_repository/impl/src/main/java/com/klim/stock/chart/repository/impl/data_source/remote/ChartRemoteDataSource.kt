package com.klim.stock.chart.repository.impl.data_source.remote

import com.klim.stock.chart.repository.impl.data_source.ChartDataSource
import com.klim.stock.chart.repository.impl.data_source.dto.ChartPriceDTO
import com.klim.stock.chart.repository.impl.map
import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.network.api.ChartApi

class ChartRemoteDataSource(private val api: ChartApi) : ChartDataSource {

    override suspend fun getSymbolChart(params: ChartUseCase.RequestParams): List<ChartPriceDTO>? {
        try {
            val response = api.getChart(symbol = params.symbol, interval = params.timeInterval.map(), range = params.range.map())
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