package com.klim.stock.history.repository.impl.data_source

import com.klim.stock.history.repository.impl.data_source.dto.HistoryPriceDTO
import com.klim.stock.history.usecase.api.HistoryUseCase

interface HistoryDataSource {

    suspend fun getSymbolPricesHistory(params: HistoryUseCase.RequestParams): List<HistoryPriceDTO>?

}