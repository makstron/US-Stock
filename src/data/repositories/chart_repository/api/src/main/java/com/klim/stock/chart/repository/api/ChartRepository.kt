package com.klim.stock.chart.repository.api

import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.chart.usecase.api.entity.SymbolChartEntity

interface ChartRepository {

    suspend fun getSymbolChart(params: ChartUseCase.RequestParams, forceUpdate: Boolean = false): List<SymbolChartEntity>?

}