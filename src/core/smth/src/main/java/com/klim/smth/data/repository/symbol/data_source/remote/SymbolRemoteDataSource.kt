package com.klim.smth.data.repository.symbol.data_source.remote

import com.klim.network_api.apis.SearchStockSymbolApi
import com.klim.smth.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.smth.data.repository.symbol.data_source.dto.SearchStockSymbolDTO
import com.klim.smth.data.repository.symbol.data_source.dto.SymbolDetailsDTO
import com.klim.smth.data.repository.symbol.map
import com.klim.smth.data.retrofit.models.SearchResultResponse

class SymbolRemoteDataSource(private val api: SearchStockSymbolApi) : SymbolDataSourceI {

    override suspend fun search(query: String): List<SearchStockSymbolDTO> {
        var response: SearchResultResponse? = null
        try {
            response = api.search(query)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        response?.results?.let { results ->
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