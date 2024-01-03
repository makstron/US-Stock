package com.klim.stock.history.repository

import com.klim.stock.history.repository.data_source.HistoryPriceDTO
import com.klim.stock.network.models.HistoryPriceItemResponse
import com.klim.stock.symbol.api.entity.SymbolHistoryPriceEntity

fun HistoryPriceItemResponse.map() = HistoryPriceDTO(
    time = this.time,
    close = this.close,
)

fun HistoryPriceDTO.map() = SymbolHistoryPriceEntity(
    time = this.time,
    priceClose = this.close,
)