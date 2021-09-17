package com.klim.us_stock.data.repository.history

import com.klim.us_stock.data.repository.history.data_source.HistoryPriceDTO
import com.klim.us_stock.data.retrofit.models.HistoryPriceItemResponse
import com.klim.us_stock.domain.entity.SymbolHistoryPriceEntity

fun HistoryPriceItemResponse.map() = HistoryPriceDTO(
    time = this.time,
    close = this.close,
)

fun HistoryPriceDTO.map() = SymbolHistoryPriceEntity(
    time = this.time,
    priceClose = this.close,
)