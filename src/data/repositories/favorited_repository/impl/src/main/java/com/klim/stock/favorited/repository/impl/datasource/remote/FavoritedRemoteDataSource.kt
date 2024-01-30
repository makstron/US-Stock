package com.klim.stock.favorited.repository.impl.datasource.remote

import com.klim.stock.favorited.repository.impl.datasource.FavoritedDataSource
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedPreviewDTO
import com.klim.stock.favorited.repository.impl.map
import com.klim.stock.network.api.SymbolDetailsApi

class FavoritedRemoteDataSource(private val detailsApi: SymbolDetailsApi) : FavoritedDataSource {

    override suspend fun getFavorited(): List<FavoritedDTO> {
        throw Exception("Operation is not applicable for this remote data source")
    }

    override suspend fun getFavoritedPreview(): List<FavoritedPreviewDTO> {
        throw Exception("Operation is not applicable for this remote data source")
    }

    override suspend fun getFavoritedPreview(symbols: List<String>): List<FavoritedPreviewDTO>? {
        val response = detailsApi.getDetails(symbols = symbols)
        if (response.error != null) {
            //TODO: work with errors
            return null
        } else {
            val result = response.result!!
            return result.map { it.map() }
        }
    }

    override suspend fun checkIsFavorited(symbol: String): Boolean {
        throw Exception("Operation is not applicable for this remote data source")
    }

    override suspend fun addFavorited(symbol: String) {
        throw Exception("Operation is not applicable for this remote data source")
    }

    override suspend fun addFavorited(symbol: FavoritedPreviewDTO) {
        throw Exception("Operation is not applicable for this remote data source")
    }

    override suspend fun removeFavorited(symbol: String) {
        throw Exception("Operation is not applicable for this remote data source")
    }
}