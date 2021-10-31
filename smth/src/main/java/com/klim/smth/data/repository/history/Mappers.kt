package com.klim.smth.data.repository.history

import com.klim.smth.data.repository.history.data_source.HistoryPriceDTO
import com.klim.smth.data.retrofit.models.HistoryPriceItemResponse
import com.klim.smth.domain.entity.SymbolHistoryPriceEntity

fun HistoryPriceItemResponse.map() = HistoryPriceDTO(
    time = this.time,
    close = this.close,
)

fun HistoryPriceDTO.map() = SymbolHistoryPriceEntity(
    time = this.time,
    priceClose = this.close,
)