package com.klim.stock.history.repository.impl

import com.klim.stock.history.repository.impl.data_source.dto.HistoryPriceDTO
import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.history.usecase.api.entity.SymbolHistoryPriceEntity
import com.klim.stock.network.api.HistoryApi
import com.klim.stock.network.models.HistoryPriceItemResponse

fun HistoryPriceItemResponse.map() = HistoryPriceDTO(
    time = this.time,
    close = this.close,
)

fun HistoryPriceDTO.map() = SymbolHistoryPriceEntity(
    time = this.time,
    priceClose = this.close,
)

fun HistoryUseCase.RequestParams.Range.map(): HistoryApi.Range =
    HistoryApi.Range.valueOf(this.name)

fun HistoryUseCase.RequestParams.TimeInterval.map(): HistoryApi.TimeInterval =
    HistoryApi.TimeInterval.valueOf(this.name)