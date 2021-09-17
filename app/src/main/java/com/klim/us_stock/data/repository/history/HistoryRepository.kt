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

    private val dayFormat = SimpleDateFormat("YYYY-MM-dd")

    override suspend fun getLastMonthPrices(symbol: String): List<SymbolHistoryPriceEntity>? {
        val response = historyDataSrc.getPeriodPrices(symbol, getDayWithShift(-30), getDayWithShift())
        response?.let{ list ->
            return list.map {
                it.map()
            }
        } ?: run {
            return null
        }
    }

    @Synchronized
    private fun getDayWithShift(shift: Int = 0): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, shift)
        return dayFormat.format(cal.timeInMillis)
    }


}