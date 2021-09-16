package com.klim.us_stock.data.repository.symbol.data_source.remote

import com.klim.us_stock.data.repository.symbol.data_source.StockSymbolDataSourceI
import com.klim.us_stock.data.repository.symbol.data_source.dto.SearchStockSymbolDTO
import com.klim.us_stock.data.repository.symbol.data_source.dto.SymbolDetailsDTO
import com.klim.us_stock.data.repository.symbol.map
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi
import java.lang.Exception

class StockSymbolRemoteDataSource(private val api: SearchStockSymbolApi) : StockSymbolDataSourceI {

    override suspend fun search(query: String): List<SearchStockSymbolDTO> {
        val response = api.search(query)
        response.results?.let { results ->
            return results.map {
                it.map()
            }
        } ?: run {
            return ArrayList<SearchStockSymbolDTO>()
        }
    }

    override suspend fun getDetails(symbol: String): SymbolDetailsDTO? {
        try {
            return api.getDetails(symbol).map()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}