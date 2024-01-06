package com.klim.stock.history.repository.impl

import com.klim.stock.cache.Cache
import com.klim.stock.history.repository.api.HistoryRepository
import com.klim.stock.history.repository.impl.data_source.HistoryDataSource
import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.history.usecase.api.entity.SymbolHistoryPriceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepositoryImpl
@Inject
constructor(
    private val historyDataSrc: HistoryDataSource,
    private val cache: Cache<String, List<SymbolHistoryPriceEntity>>,
) : HistoryRepository {

    override suspend fun getSymbolPricesHistory(params: HistoryUseCase.RequestParams, forceUpdate: Boolean): List<SymbolHistoryPriceEntity>? { //TODO: now now return cache here
        var list: List<SymbolHistoryPriceEntity>? = null
        if (!forceUpdate && !cache.isEmpty()) {
            list = cache.get(params.symbol)
        }
        if (list == null) {
            historyDataSrc.getSymbolPricesHistory(params)?.let { historyPrices ->
                list = historyPrices.map { it.map() }
                cache.set(params.symbol, list)
            }
        }
        return list
    }

}