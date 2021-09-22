package com.klim.us_stock.data.repository.history

import com.klim.us_stock.data.repository.history.data_source.HistoryDataSourceI
import com.klim.us_stock.domain.entity.SymbolHistoryPriceEntity
import com.klim.us_stock.domain.repository.HistoryRepositoryI
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HistoryRepository
@Inject
constructor(
    private val historyDataSrc: HistoryDataSourceI
) : HistoryRepositoryI {

    override suspend fun getPricesForPeriod(symbol: String, olderDate: String, newerDate: String): List<SymbolHistoryPriceEntity>? {
        val response = historyDataSrc.getPeriodPrices(symbol, olderDate, newerDate)
        response?.let { list ->
            return list.map {
                it.map()
            }
        } ?: run {
            return null
        }
    }

}