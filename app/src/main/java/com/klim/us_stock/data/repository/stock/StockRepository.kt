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

    override suspend fun getPriceForDate(symbol: String, date: String): SymbolPriceEntity? {
        return remoteDataSrc.getPrice(symbol, date)?.map()
    }

}