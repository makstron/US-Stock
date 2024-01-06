package com.klim.stock.history.repository.api

import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.history.usecase.api.entity.SymbolHistoryPriceEntity

interface HistoryRepository {

    suspend fun getSymbolPricesHistory(params: HistoryUseCase.RequestParams, forceUpdate: Boolean = false): List<SymbolHistoryPriceEntity>?

}