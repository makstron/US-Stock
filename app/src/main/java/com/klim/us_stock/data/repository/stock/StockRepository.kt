package com.klim.us_stock.data.repository.stock

import com.klim.us_stock.data.repository.stock.data_source.StockDataSourceI
import com.klim.us_stock.data.repository.stock.data_source.map
import com.klim.us_stock.domain.entity.SymbolPriceEntity
import com.klim.us_stock.domain.repository.StockRepositoryI
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StockRepository
@Inject
constructor(
    private val remoteDataSrc: StockDataSourceI
) : StockRepositoryI {

    private val dayFormat = SimpleDateFormat("YYYY-MM-dd").apply {
        timeZone = TimeZone.getTimeZone("GMT-5:00")
    }

    private val dayFormatTest = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").apply {
        timeZone = TimeZone.getTimeZone("GMT-5:00")
    }

    override suspend fun getLastPrice(symbol: String): SymbolPriceEntity? {
        //I can get price only from yesterday because of free api
        val date = getDayWithShift(-1)
        return remoteDataSrc.getPrice(symbol, date)?.map()
    }

    override suspend fun getPreviousPrice(symbol: String): SymbolPriceEntity? {
        val date = getDayWithShift(-2)
        return remoteDataSrc.getPrice(symbol, date)?.map()
    }

    @Synchronized
    private fun getDayWithShift(shift: Int = 0): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, shift)
        println("generated date " + dayFormatTest.format(cal.timeInMillis))
        return dayFormat.format(cal.timeInMillis)
    }
}