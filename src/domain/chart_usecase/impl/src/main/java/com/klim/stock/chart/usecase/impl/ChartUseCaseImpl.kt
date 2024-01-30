package com.klim.stock.chart.usecase.impl

import com.klim.stock.chart.repository.api.ChartRepository
import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.chart.usecase.api.entity.SymbolChartEntity
import javax.inject.Inject

class ChartUseCaseImpl
@Inject constructor(
    private val repositoryChart: ChartRepository,
) : ChartUseCase {

    override suspend fun getSymbolChartData(params: ChartUseCase.RequestParams): List<SymbolChartEntity>? {
        return repositoryChart.getSymbolChart(params)
    }

}