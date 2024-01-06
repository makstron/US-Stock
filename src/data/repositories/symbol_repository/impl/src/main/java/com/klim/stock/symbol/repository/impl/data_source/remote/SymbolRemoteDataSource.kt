package com.klim.stock.symbol.repository.impl.data_source.remote

import com.klim.stock.network.models.search.SearchResponse
import com.klim.stock.network.api.SearchApi
import com.klim.stock.network.api.SymbolDetailsApi
import com.klim.stock.symbol.repository.impl.data_source.SymbolDataSourceI
import com.klim.stock.symbol.repository.impl.data_source.dto.SearchStockSymbolDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SymbolDetailsDTO
import com.klim.stock.symbol.repository.impl.map
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class SymbolRemoteDataSource(
    private val searchApi: SearchApi,
    private val detailsApi: SymbolDetailsApi,
) : SymbolDataSourceI {

    override suspend fun search(query: String): List<SearchStockSymbolDTO> {
        var response: SearchResponse? = null
        try {
            response = searchApi.searchY(query = query)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        response?.finance?.result?.first()?.results?.let { results ->
            return results.map {
                it.map()
            }
        } ?: run {
            return ArrayList<SearchStockSymbolDTO>()
        }
    }

    override suspend fun getDetails(symbol: String): SymbolDetailsDTO? = coroutineScope {
        val detailsSummary = async { detailsApi.getDetailsSummary(symbol = symbol) }
        val details = async { detailsApi.getDetails(symbol = symbol) }

        try {
            //TODO: now check for errors
            return@coroutineScope map(details.await().result, detailsSummary.await().result)
        } catch (e: Exception) {
            e.printStackTrace()
            return@coroutineScope null
        }
    }
}