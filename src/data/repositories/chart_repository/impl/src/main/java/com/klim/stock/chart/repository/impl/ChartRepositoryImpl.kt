package com.klim.stock.chart.repository.impl

import com.klim.stock.cache.Cache
import com.klim.stock.chart.repository.api.ChartRepository
import com.klim.stock.chart.repository.impl.data_source.ChartDataSource
import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.chart.usecase.api.entity.SymbolChartEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChartRepositoryImpl
@Inject
constructor(
    private val chartDataSrc: ChartDataSource,
    private val cache: Cache<String, List<SymbolChartEntity>>,
) : ChartRepository {

    override suspend fun getSymbolChart(params: ChartUseCase.RequestParams, forceUpdate: Boolean): List<SymbolChartEntity>? { //TODO: now now return cache here
        var list: List<SymbolChartEntity>? = null
        if (!forceUpdate && !cache.isEmpty()) {
            list = cache.get(params.symbol)
        }
        if (list == null) {
            chartDataSrc.getSymbolChart(params)?.let { chartItem ->
                list = chartItem.map { it.map() }
                cache.set(params.symbol, list)
            }
        }
        return list
    }

}