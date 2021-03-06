package com.klim.coreUi.data.repository.history

import com.klim.cache.Cache
import com.klim.coreUi.data.repository.history.data_source.HistoryDataSourceI
import com.klim.coreUi.domain.entity.SymbolHistoryPriceEntity
import com.klim.coreUi.domain.repository.HistoryRepositoryI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository
@Inject
constructor(
    private val historyDataSrc: HistoryDataSourceI,
    private val cache: Cache<String, List<SymbolHistoryPriceEntity>>,
) : HistoryRepositoryI {

    //TODO now cache works every time because limit of free API
    override suspend fun getPricesForPeriod(symbol: String, olderDate: String, newerDate: String, forceUpdate: Boolean): List<SymbolHistoryPriceEntity>? {
        var list: List<SymbolHistoryPriceEntity>? = null
        if (!forceUpdate && !cache.isEmpty()) {
            list = cache.get(symbol)
        }
        if (list == null) {
            historyDataSrc.getPeriodPrices(symbol, olderDate, newerDate)?.let {historyPrices ->
                list = historyPrices.map{it.map()}
                cache.set(symbol, list)
            }
        }
        return list
    }

}