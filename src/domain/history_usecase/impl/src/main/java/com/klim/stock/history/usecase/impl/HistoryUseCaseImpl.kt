package com.klim.stock.history.usecase.impl

import com.klim.stock.history.repository.api.HistoryRepository
import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.history.usecase.api.entity.SymbolHistoryPriceEntity
import javax.inject.Inject

class HistoryUseCaseImpl
@Inject constructor(
    private val repositoryHistory: HistoryRepository,
) : HistoryUseCase {

    override suspend fun getSymbolPricesHistory(params: HistoryUseCase.RequestParams): List<SymbolHistoryPriceEntity>? {
        return repositoryHistory.getSymbolPricesHistory(params)
    }

}