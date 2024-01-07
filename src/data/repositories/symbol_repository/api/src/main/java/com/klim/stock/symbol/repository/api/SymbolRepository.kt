package com.klim.stock.symbol.repository.api

import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsRepositoryEntity

interface SymbolRepository {

    suspend fun search(query: String): List<SearchResultEntity>

    suspend fun getDetails(symbol: String): SymbolDetailsRepositoryEntity?
}