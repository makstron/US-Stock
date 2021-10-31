package com.klim.smth.data.repository.stock.data_source.remote

import com.klim.smth.data.repository.stock.data_source.StockDataSourceI
import com.klim.smth.data.repository.stock.data_source.dto.SymbolPriceDTO
import com.klim.smth.data.repository.stock.data_source.map
import com.klim.smth.data.retrofit.apis.StockSymbolApi
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