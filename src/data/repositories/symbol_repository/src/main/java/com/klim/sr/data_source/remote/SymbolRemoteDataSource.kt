package com.klim.sr.data_source.remote

import com.klim.coreUi.data.retrofit.models.SearchResultResponse
import com.klim.network_api.apis.SearchStockSymbolApi
import com.klim.sr.data_source.SymbolDataSourceI
import com.klim.sr.data_source.dto.SearchStockSymbolDTO
import com.klim.sr.data_source.dto.SymbolDetailsDTO
import com.klim.sr.map

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