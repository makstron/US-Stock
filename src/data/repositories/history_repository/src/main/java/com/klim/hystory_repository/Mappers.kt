package com.klim.coreUi.data.repository.history

import com.klim.coreUi.data.repository.history.data_source.HistoryPriceDTO
import com.klim.coreUi.data.retrofit.models.HistoryPriceItemResponse
import com.klim.coreUi.domain.entity.SymbolHistoryPriceEntity

fun HistoryPriceItemResponse.map() = HistoryPriceDTO(
    time = this.time,
    close = this.close,
)

fun HistoryPriceDTO.map() = SymbolHistoryPriceEntity(
    time = this.time,
    priceClose = this.close,
)