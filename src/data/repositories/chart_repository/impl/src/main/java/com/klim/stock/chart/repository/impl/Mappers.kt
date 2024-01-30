package com.klim.stock.chart.repository.impl

import com.klim.stock.chart.repository.impl.data_source.dto.ChartPriceDTO
import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.chart.usecase.api.entity.SymbolChartEntity
import com.klim.stock.network.api.ChartApi
import com.klim.stock.network.models.ChartItemResponse

fun ChartItemResponse.map() = ChartPriceDTO(
    time = this.time,
    close = this.close,
)

fun ChartPriceDTO.map() = SymbolChartEntity(
    time = this.time,
    priceClose = this.close,
)

fun ChartUseCase.RequestParams.Range.map(): ChartApi.Range =
    ChartApi.Range.valueOf(this.name)

fun ChartUseCase.RequestParams.TimeInterval.map(): ChartApi.TimeInterval =
    ChartApi.TimeInterval.valueOf(this.name)