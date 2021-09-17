package com.klim.us_stock.data.repository.stock.data_source.remote

import com.klim.us_stock.data.repository.stock.data_source.StockDataSourceI
import com.klim.us_stock.data.repository.stock.data_source.dto.SymbolPriceDTO
import com.klim.us_stock.data.repository.stock.data_source.map
import com.klim.us_stock.data.retrofit.apis.StockSymbolApi
import java.lang.Exception

class StockRemoteDataSource(private val api: StockSymbolApi) : StockDataSourceI {

    override suspend fun getPrice(symbol: String, date: String): SymbolPriceDTO? {
        return try {
            api.getPrice(symbol, date).map()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}