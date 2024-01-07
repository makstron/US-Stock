package com.klim.stock.symbol.repository.impl.data_source.remote

import com.klim.stock.network.api.SearchApi
import com.klim.stock.network.api.SymbolDetailsApi
import com.klim.stock.network.models.search.SearchResponse
import com.klim.stock.symbol.repository.impl.data_source.SymbolDataSourceI
import com.klim.stock.symbol.repository.impl.data_source.dto.SearchStockSymbolDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SymbolDetailsDTO
import com.klim.stock.symbol.repository.impl.map
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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
        val detailsSummaryDeferred = async { detailsApi.getDetailsSummary(symbol = symbol) }
        val detailsDeferred = async { detailsApi.getDetails(symbol = symbol) }
        val recommendationDeferred = async { detailsApi.getRecommendations(symbol = symbol) }

        try {
            //TODO: now check for errors
            val detailsSummary = detailsSummaryDeferred.await()
            val details = detailsDeferred.await()
            val recommendation = recommendationDeferred.await()

            val detailsSummaryResult = detailsSummary.result
            val detailsResult = details.result
            val recommendationResult = recommendation.result
            if (detailsSummaryResult != null && detailsResult != null && recommendationResult != null) {
                return@coroutineScope map(detailsSummaryResult, detailsResult, recommendationResult)
            } else {
                //error
                return@coroutineScope null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@coroutineScope null
        }
    }
}