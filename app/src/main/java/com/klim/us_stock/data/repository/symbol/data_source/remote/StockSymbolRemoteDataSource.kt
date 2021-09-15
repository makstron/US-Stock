package com.klim.us_stock.data.repository.symbol.data_source.remote

import com.klim.us_stock.data.repository.symbol.data_source.StockSymbolDataSourceI
import com.klim.us_stock.data.repository.symbol.data_source.SearchStockSymbolDTO
import com.klim.us_stock.data.repository.symbol.map
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi

class StockSymbolRemoteDataSource(private val api: SearchStockSymbolApi) : StockSymbolDataSourceI {

    override suspend fun search(query: String): List<SearchStockSymbolDTO> {
        val response = api.allPosts(query)
        response.results?.let { results ->
            return results.map {
                it.map()
            }
        } ?: run {
            return ArrayList<SearchStockSymbolDTO>()
        }
    }
}