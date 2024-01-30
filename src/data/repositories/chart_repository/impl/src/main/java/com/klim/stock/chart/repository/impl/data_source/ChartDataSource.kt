package com.klim.stock.chart.repository.impl.data_source

import com.klim.stock.chart.repository.impl.data_source.dto.ChartPriceDTO
import com.klim.stock.chart.usecase.api.ChartUseCase

interface ChartDataSource {

    suspend fun getSymbolChart(params: ChartUseCase.RequestParams): List<ChartPriceDTO>?

}