package com.klim.stock.stock.repository.data_source.remote

import com.klim.stock.network.api.StockSymbolApi
import com.klim.stock.stock.repository.data_source.StockDataSourceI
import com.klim.stock.stock.repository.data_source.dto.SymbolPriceDTO
import com.klim.stock.stock.repository.data_source.map

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